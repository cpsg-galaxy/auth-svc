@Library('ao-shared-library')

import com.cisco.jenkins.ccs.ao_workflows

// Configuration used to access CCS and call Work Flows
CCS_URL="https://na.cloudcenter.cisco.com"
CCS_CREDENTIALS="ccs_cred"
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
        DEPLOYMENT_WORKFLOW_PARAMS="""
            {
              "version": "${BUILD_ID}"
            }
        """
        try {
            ao_wf = new ao_workflows()
            wf_id=ao_wf.run(DEPLOYMENT_WORKFLOW_NAME, DEPLOYMENT_WORKFLOW_PARAMS, CCS_URL, CCS_CREDENTIALS)

            timeout = 70
            wf_status = ''
            while ((!(wf_status in ['success','failed','canceled'])) && (timeout > 0)) {
                sleep 10
                timeout--
                wf_status = ao_wf.status(wf_id, CCS_URL, CCS_CREDENTIALS)
                println('Current status: ' + wf_status)
            }

            if ((timeout == 0) || (wf_status != 'success')) {
                error('Workflow '+ DEPLOYMENT_WORKFLOW_NAME + ' did not complete successfully')
            }
        } catch(Exception ex) {
            updateGitlabCommitStatus name: STAGE_TITLE, state: 'failed'
            error("Deployment failed")
        }
    }
}