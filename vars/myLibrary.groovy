def buildApp(appimage,apptag) {
    echo "build"
    container('docker') {
                echo "Building docker image..."

                sh "docker build -t ${appimage}:${apptag} ."
                sh "docker tag ${appimage}:${apptag} ${appimage}:latest"
            }
}

def pushApp(appimage,apptag) {
    echo "push"
    container('docker') {
                sh "docker push ${appimage}:${apptag}"
                sh "docker push ${appimage}:latest"
            }
}

def cleanup() {
    echo "cleanup"
}