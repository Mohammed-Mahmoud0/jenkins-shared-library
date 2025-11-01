def call(String imageName) {
    pipeline {
        agent any

        stages {
            stage('Build Maven') {
                steps {
                    sh "mvn clean install -DskipTests"
                }
            }
            stage('Docker Build & Push') {
                steps {
                    sh "docker build -t ${imageName}:${env.BUILD_NUMBER} ."
                    sh "docker login -u mohamedmahmoud00 -p ${env.DOCKERHUB_PASS}"
                    sh "docker push ${imageName}:${env.BUILD_NUMBER}"
                }
            }
        }
    }
}
