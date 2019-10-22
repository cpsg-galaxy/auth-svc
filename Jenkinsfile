node('sample') {
    checkout scm

    // Testing and Code Coverage
    stage('test') {
        sh('make test')
    }

    // Building Binary
    stage('build') {
        sh('sudo rm -rf $PWD/build')
        sh('make build')
    }

    // Building and Pushing Image to Registry
    stage('push') {
        sh('make push')
    }
}