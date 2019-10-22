node('sample') {
    checkout scm
    stage('test') {
        sh('make test')
    }
    stage('build') {
        sh('make build')
    }
}