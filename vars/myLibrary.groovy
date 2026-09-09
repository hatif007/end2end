def ssonarCreateProject(String projectKey) {
    echo "scan code"
     sonarLocalScan()
}


def scanApp() {
    echo "scan code"
     sonarLocalScan()
}


    container('docker') {
          

    }
def buildApp(appimage, apptag) {
    echo "build"

    container('docker') {

        sh '''
            echo "Waiting for Docker daemon..."
            until docker info >/dev/null 2>&1; do
                sleep 1
            done
        '''

        echo "Building docker image..."

        sh "docker build -t ${appimage}:${apptag} ."
        sh "docker tag ${appimage}:${apptag} ${appimage}:latest"
    }
}

def pushApp(appimage, apptag) {
    echo "push"

    container('docker') {

        withCredentials([
            usernamePassword(
                credentialsId: 'dockerhub',
                usernameVariable: 'DOCKER_USER',
                passwordVariable: 'DOCKER_PASS'
            )
        ]) {

            sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'

            sh "docker push ${appimage}:${apptag}"
            sh "docker push ${appimage}:latest"
        }
    }
}

def cleanup() {
    echo "cleanup"
}