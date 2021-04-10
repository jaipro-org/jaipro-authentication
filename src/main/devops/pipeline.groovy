node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID   ='GITHUB_PPCC'
    def MASTER_FOLDER               ='master'

    // SERVICE PROPS
    def SVC_NAME        = 'eureka-authentication'
    def SVC_FOLDER      = 'SERVICE'

    stage('INIT') {
        sh "echo '******INITIALIZING.....'"

    }

    stage('FETCHING SERVICE PROPERTIES') {
        dir(MASTER_FOLDER) {
            sh "echo '****** STARTING PHASE: fetching service properties'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/master-properties.git'
        }
    }

    stage('INIT2') {
        dir(SVC_FOLDER) {
            sh "echo '****** STARTING PHASE: init'"
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: "https://github.com/bindord-org/$SVC_NAME.git"
        }
    }

}
