node('sample') {
    checkout scm
    stage('test') {
        sh('make test')
    }
    stage('build') {
        sh('echo $PWD')
        sh('make build')
    }
}