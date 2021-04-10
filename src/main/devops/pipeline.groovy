node {

    // FUNDAMENTAL_PROPS
    def GIT_MASTER_CREDENTIALS_ID   ='GITHUB_PPCC'
    def MASTER_FOLDER               ='master'

    // SERVICE PROPS
    def SVC_NAME        = 'eureka-authentication'
    def SVC_FOLDER      = 'SERVICE'


    stage('FETCHING SERVICE PROPERTIES') {
        dir(MASTER_FOLDER) {
            git branch: 'main', credentialsId: GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/master-properties.git'
        }
    }

    stage('INIT ') {
        dir(SVC_FOLDER) {
            git branch: 'main', credentialsId: $GIT_MASTER_CREDENTIALS_ID, url: 'https://github.com/bindord-org/$SVC_NAME.git'
        }
    }

}
