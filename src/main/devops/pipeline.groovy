String getPropValueFromProperties(String property) {
    for (int i = 0; i < 3; i++) {
        sh "echo '>>>>>>>TEST GETPROPVALUEFROMPROPERTIES: $i'"
    }
    return sh(script: "cat src/main/resources/application.properties " +
            "| grep '$property=' " +
            "| awk -F '$property=' '{print \$2}'",
            returnStdout: true).trim()
}

void replaceVariablesInProperties(property, propertyFile) {
    sh "echo '*** Init replaceVariablesInProperties'"
    def commandString = "sed"
    property.each { item ->
        def keyVal = item.split(':')
        commandString += " -e \"s/\\${keyVal[0]}/${keyVal[1]}/\""
    }
    commandString += " -i $propertyFile"

    sh "echo 'commandString: $commandString'"
    sh "$commandString"
    sh "cat $propertyFile"
}

node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID = 'GITHUB_PPCC'
    def MASTER_FOLDER = 'master'
    def DEPLOY_ENV = 'dev'
    def K8S_LOCAL = 'K8S_CONFIG_ID_LOCAL'
    // - BASE PATHS
    def JOB_NAME = env.JOB_NAME
    def JOB_FULLPATH = env.WORKSPACE
    def BASE_CONFIGMAP = 'base/config-map-base.yaml'
    def MVN_REPOSITORY = '/root/.m2'

    // SERVICE PROPS
    def SVC_REPOSITORY_URL = scm.userRemoteConfigs[0].url
    def PRODUCT_NAME = 'hogarep'
    def SVC_FOLDER = 'app'
    def SVC_NAME = ''
    def APPLICATION_PROPERTIES_PATH = ''
    def SVC_FULLPATH = '/home/ubuntu/jenkins/jenkins_home/workspace' + '/' + JOB_NAME + '/' + SVC_FOLDER

    //DOCKER REGISTRY PROPS
    def CR_BINDORD_HOST = "peterzinho16"
    def SVC_IMAGE = ''

    stage('PRINT VARIABLES') {
        sh "echo 'SVC_REPOSITORY_URL: $SVC_REPOSITORY_URL'"
        sh "echo 'JOB_FULLPATH: $JOB_FULLPATH'"
        sh "echo 'JOB_NAME: $JOB_NAME'"
        sh "echo '******INITIALIZING.....'"
    }

    stage('FETCHING SERVICE SOURCES') {
        sh "echo '****** STARTING PHASE: fetching service sources'"

        dir(SVC_FOLDER) {
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: SVC_REPOSITORY_URL

            SVC_NAME = getPropValueFromProperties('spring.application.name')
            APPLICATION_PROPERTIES_PATH = "$SVC_NAME/application-$DEPLOY_ENV" + ".yaml"
        }
    }

    stage('FETCHING SERVICE PROPERTIES') {
        sh "echo '****** STARTING PHASE: fetching service properties'"

        dir(MASTER_FOLDER) {
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/master-properties.git'

            def keyValueProps = [
                    "SVC_NAME:$SVC_NAME",
                    "PRODUCT_NAME:$PRODUCT_NAME"
            ]
            replaceVariablesInProperties(keyValueProps, BASE_CONFIGMAP)

            sh "sed -i 's/^/    /' $APPLICATION_PROPERTIES_PATH"
            sh "cat $APPLICATION_PROPERTIES_PATH >> $BASE_CONFIGMAP"
            sh "cat $BASE_CONFIGMAP"
        }
    }

    stage('DEPLOYING CONFIGMAP') {
        sh 'echo "INIT K8S...."'

        withKubeConfig([credentialsId: K8S_LOCAL]) {
            sh "kubectl apply -f $MASTER_FOLDER/$BASE_CONFIGMAP"
        }
    }

    /*stage('TESTING') {
        sh "echo '****** STARTING PHASE: testing'"

        sh "docker run -i --rm -p 8080:8080 " +
                "-v $SVC_FULLPATH:/$SVC_FOLDER " +
                "-v $MVN_REPOSITORY:$MVN_REPOSITORY " +
                "-w /$SVC_FOLDER " +
                "maven:3.8.1-openjdk-11-slim " +
                "mvn test"
    }*/

    stage('COMPILING AND PUSHING IMAGE') {
        sh "echo '****** STARTING PHASE: compiling and pushing image'"

        sh "docker run -i --rm -p 8080:8080 " +
                "-v $SVC_FULLPATH:/$SVC_FOLDER " +
                "-v $MVN_REPOSITORY:$MVN_REPOSITORY " +
                "-w /$SVC_FOLDER " +
                "maven:3.8.1-openjdk-11-slim " +
                "mvn clean package"

        def SVC_VERSION = sh(script: "cat $SVC_FOLDER/pom.xml " +
                '| grep -B 1 \'name\' ' +
                '| grep \'<version>\' ' +
                '| sed -e \'s/^[[:space:]]*//\' | cut -c 10- | rev | cut -c 11- | rev',
                returnStdout: true).trim()

        SVC_IMAGE = "$CR_BINDORD_HOST/$SVC_NAME:$SVC_VERSION"

        sh "echo 'SVC_VERSION: ${SVC_VERSION}--'"
        sh "docker build " +
                "-t $SVC_IMAGE " +
                "-f ./$SVC_FOLDER/src/main/devops/Dockerfile " +
                "./$SVC_FOLDER/target"
    }

    stage('DEPLOYING TO K8S') {

        dir(SVC_FOLDER) {

            def IMAGE_PARAM = '${SVC_IMAGE}'
            def SVC_NAME_PARAM = '${SVC_NAME}'

            sh "sed -e 's|\\$IMAGE_PARAM|$SVC_IMAGE|' \\" +
                    "-e 's|\\$SVC_NAME_PARAM|$SVC_NAME|' -i \\" +
                    'src/main/devops/deployment.yaml'

            sh "cat src/main/devops/deployment.yaml"

            withKubeConfig([credentialsId: K8S_LOCAL]) {
                sh "kubectl apply -f src/main/devops/deployment.yaml"
            }
        }

    }

    stage('EXPOSING SERVICE') {

        dir(SVC_FOLDER) {

            def CONTEX_PATH_PARAM = 'SERVICE_INGRESS_CONTEXT_PATH'

            def SVC_CONTEXT_PATH = getPropValueFromProperties('service.ingress.context-path')

            sh "sed -e 's|\\$CONTEX_PATH_PARAM|$SVC_CONTEXT_PATH|' -i \\" +
                    'src/main/devops/ingress.yaml'

            sh "cat src/main/devops/ingress.yaml"

            withKubeConfig([credentialsId: K8S_LOCAL]) {
                sh "kubectl apply -f src/main/devops/service-nodeport.yaml"
                sh "kubectl apply -f src/main/devops/ingress.yaml"
            }

        }

    }


}
