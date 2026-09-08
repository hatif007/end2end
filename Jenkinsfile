@Library('my-shared-library') _

def appname = "hello-newapp"
def repo = "hatif007"
def appimage = "docker.io/${repo}/${appname}"
def apptag = "${env.BUILD_NUMBER}"

podTemplate(
    cloud: 'Kubernetes',
    containers: [
        containerTemplate(
            name: 'jnlp',
            image: 'jenkins/inbound-agent:latest'
        ),
        containerTemplate(
            name: 'docker',
            image: 'docker:26-dind',
            privileged: true,
            args: '--storage-driver=vfs'
        )
    ],
    volumes: [
        emptyDirVolume(
            mountPath: '/var/lib/docker',
            memory: false
        )
    ]
) {
    node(POD_LABEL) {

        stage('checkout') {
            container('jnlp') {
                sh '/usr/bin/git config --global http.sslVerify false'
                checkout scm
            }
        }

        stage('build') {
            myLibrary.buildApp(appimage, apptag)
        }

        stage('push') {
            myLibrary.pushApp(appimage, apptag)
        }

    }
}