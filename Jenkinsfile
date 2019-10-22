node('docker') {
    checkout scm
    stage('test') {
        docker.image('go').inside {
            sh 'go version'
        }
    }
}