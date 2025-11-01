def call(String imageName) {
    node('node1') {
        stage('Build Maven') {
            dir("${env.WORKSPACE}") {
                sh "mvn clean install -DskipTests"
            }
        }

        stage('Docker Build & Push') {
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
