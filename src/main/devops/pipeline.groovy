node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID   ='GITHUB_PPCC'
    def MASTER_FOLDER               ='master'
    // - BASE PATHS
    def JOB_NAME            = env.JOB_NAME
    def JOB_FULLPATH_CON    = '/var/jenkins_home/workspace/' + JOB_NAME
    def JOB_FULLPATH        = env.WORKSPACE
    def BASE_CONFIGMAP      = './base/config-map-base.yaml'

    // SERVICE PROPS
    def PRODUCT_NAME    = 'hogarep'
    def SVC_NAME        = 'eureka-authentication'
    def SVC_FOLDER      = 'service'

    stage('PRINT VARIABLES') {
        sh "echo 'JOB_FULLPATH: $JOB_FULLPATH'"
        sh "echo 'JOB_NAME: $JOB_NAME'"
        sh "echo '******INITIALIZING.....'"
    }

    stage('FETCHING SERVICE PROPERTIES') {
        dir(MASTER_FOLDER) {
            sh "echo '****** STARTING PHASE: fetching service properties'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/master-properties.git'

            sh "sed -ie \"s/\\SVC_NAME/$SVC_NAME/\" \\" +
                   "-ie \"s/\\PRODUCT_NAME/$PRODUCT_NAME/\" \\" +
                    BASE_CONFIGMAP
            sh "cat $BASE_CONFIGMAP"
        }
    }

    /*stage('INIT2') {
        dir(SVC_FOLDER) {
            sh "echo '****** STARTING PHASE: init'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: "https://github.com/bindord-org/$SVC_NAME"+".git"
        }
    }*/

}
