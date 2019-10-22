node('sample') {
    checkout scm
    stage('test') {
        sh('make test')
    }
    stage('build') {
        sh('rm -rf $PWD/build')
        sh('make build')
    }
}