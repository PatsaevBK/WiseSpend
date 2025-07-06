pipeline {
    agent {
        docker {
            image 'jenkins-android-agent:latest'
            args '-v $HOME/.gradle:/home/jenkins/.gradle'
        }
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Debug APK') {
            steps {
                sh './gradlew :androidApp:assembleDebug'
            }
        }
        stage('Publish Debug APK to Maven Local') {
            steps {
                sh './gradlew :androidApp:publishDebugApkPublicationToMavenLocal'
            }
        }
    }
    post {
        always {
            script {
                if (getContext(hudson.FilePath)) {
                    cleanWs()
                } else {
                    echo "Workspace is not available for cleanWs()"
                }
            }
        }
    }
}
