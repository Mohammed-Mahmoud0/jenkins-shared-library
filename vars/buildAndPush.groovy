def call(String imageName) {

    stage('Build Maven') {
        dir("${env.WORKSPACE}") {   // make sure we are in the main workspace
            sh "ls -la"              // debug (optional)
            sh "mvn clean install -DskipTests"
        }
    }

    stage('Docker Build & Push') {
        dir("${env.WORKSPACE}") {
            withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKERHUB_PASS')]) {
                sh """
                docker build -t ${imageName}:${env.BUILD_NUMBER} .
                echo "$DOCKERHUB_PASS" | docker login -u mohamedmahmoud00 --password-stdin
                docker push ${imageName}:${env.BUILD_NUMBER}
                """
            }
        }
    }
}
