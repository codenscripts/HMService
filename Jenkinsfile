pipeline {
    agent any

    tools {
        maven 'Maven 3.9' // Match the name from Jenkins Tools config
    }

    environment {
        DOCKERHUB_USERNAME = 'omarcy'
        APP_NAME = 'hotel-management-app'
        IMAGE_NAME = "${DOCKERHUB_USERNAME}/${APP_NAME}:${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Code checked out.'
            }
        }

        stage('Build & Test') {
            steps {

                sh 'mvn clean verify'
                echo 'Maven build & tests complete.'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${env.IMAGE_NAME} ."
                echo "Docker image ${env.IMAGE_NAME} built."
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')])
                    sh """
                        echo "${env.DOCKER_PASS}" | docker login -u "${env.DOCKER_USER}" --password-stdin
                    """

                    sh "docker push ${env.IMAGE_NAME}"
                    echo "Successfully pushed ${env.IMAGE_NAME} to Docker Hub."

                    sh "docker logout"
                }
            }
        }
    }

    post {
        always { echo "Pipeline finished. Status: ${currentBuild.currentResult}" }
        success { echo 'Pipeline succeeded!' }
        failure { echo 'Pipeline failed!' }
    }
}