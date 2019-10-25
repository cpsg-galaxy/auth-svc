@Library('ao-shared-library')

import com.cisco.jenkins.ccs.ao_workflows

// Configuration used to access CCS and call Work Flows
CCS_URL="https://na.cloudcenter.cisco.com"
CCS_CREDENTIALS="1245696:02c1629d-bec8-4673-8256-626be7934a29"
DEPLOYMENT_WORKFLOW_NAME="CI_CD"

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

    // Triggering AO Workflow for Deployment
    stage('deploy') {
        DEPLOYMENT_WORKFLOW_PARAMS = ""

        ao_wf = new ao_workflows()
        wf_id=ao_wf.run(DEPLOYMENT_WORKFLOW_NAME, DEPLOYMENT_WORKFLOW_PARAMS, CCS_URL, CCS_CREDENTIALS)
    }
}