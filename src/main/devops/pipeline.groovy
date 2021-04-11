node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID   = 'GITHUB_PPCC'
    def MASTER_FOLDER               = 'master'
    def DEPLOY_ENV                  = 'dev'
    def K8S_LOCAL                   = 'K8S_CONFIG_ID_LOCAL'
    // - BASE PATHS
    def JOB_NAME            = env.JOB_NAME
    def JOB_FULLPATH        = env.WORKSPACE
    def BASE_CONFIGMAP      = 'base/config-map-base.yaml'

    // SERVICE PROPS
    def SVC_REPOSITORY_URL = scm.userRemoteConfigs[0].url
    def PRODUCT_NAME    = 'hogarep'
    def SVC_NAME        = 'eureka-authentication'
    def SVC_FOLDER      = "service-$SVC_NAME"
    def APPLICATION_PROPERTIES_PATH = "$SVC_NAME/application-$DEPLOY_ENV" + ".yaml"

    stage('PRINT VARIABLES') {
        sh "echo 'SVC_REPOSITORY_URL: $SVC_REPOSITORY_URL'"
        sh "echo 'JOB_FULLPATH: $JOB_FULLPATH'"
        sh "echo 'JOB_NAME: $JOB_NAME'"
        sh "echo '******INITIALIZING.....'"
    }

    stage('FETCHING SERVICE PROPERTIES') {
        dir(MASTER_FOLDER) {
            sh "echo '****** STARTING PHASE: fetching service properties'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/master-properties.git'

            sh "sed -e \"s/\\SVC_NAME/$SVC_NAME/\" \\" +
                   "-e \"s/\\PRODUCT_NAME/$PRODUCT_NAME/\" -i \\" +
                    BASE_CONFIGMAP
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

    stage('FETCHING SERVICE SOURCES') {
        dir(SVC_FOLDER) {
            sh "echo '****** STARTING PHASE: fetching service sources'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: SVC_REPOSITORY_URL
        }
    }

    stage('TESTING') {
        sh "docker run -i --rm -p 8383:8080 -v /root/.m2/:/root/.m2/ -w ./$SVC_FOLDER maven:3-alpine mvn test"
    }

}
