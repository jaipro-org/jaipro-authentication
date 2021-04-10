node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID   = 'GITHUB_PPCC'
    def MASTER_FOLDER               = 'master'
    def DEPLOY_ENV                  = 'dev'
    def K8S_LOCAL                   = 'K8S_CONFIG_ID_LOCAL'
    // - BASE PATHS
    def JOB_NAME            = env.JOB_NAME
    def JOB_FULLPATH_CON    = '/var/jenkins_home/workspace/' + JOB_NAME
    def JOB_FULLPATH        = env.WORKSPACE
    def BASE_CONFIGMAP      = 'base/config-map-base.yaml'

    // SERVICE PROPS
    def PRODUCT_NAME    = 'hogarep'
    def SVC_NAME        = 'eureka-authentication'
    def SVC_FOLDER      = 'service'
    def APPLICATION_PROPERTIES_PATH = "$SVC_NAME/application-$DEPLOY_ENV" + ".yaml"

    stage('PRINT VARIABLES') {
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

    stage('DEPLOYING TO K8S') {
        sh 'echo "INIT K8S...."'
        //acsDeploy azureCredentialsId: 'AKS_BINDORD_CLIENT', configFilePaths: 'app/src/main/devops/deployment.yml', containerService: 'cluster-webapps | AKS', dcosDockerCredentialsPath: '', resourceGroupName: 'rg-devops', secretName: '', sshCredentialsId: '96a4e79f-641b-4521-9a35-d41bf54224e8'
        kubernetesDeploy configs: "$JOB_FULLPATH/$MASTER_FOLDER/$BASE_CONFIGMAP", kubeconfigId: K8S_LOCAL, enableConfigSubstitution: true
    }

    /*stage('INIT2') {
        dir(SVC_FOLDER) {
            sh "echo '****** STARTING PHASE: init'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: "https://github.com/bindord-org/$SVC_NAME"+".git"
        }
    }*/

}
