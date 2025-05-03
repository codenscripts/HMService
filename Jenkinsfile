pipeline {
    agent any

    tools {
        maven 'Maven 3.9' // Match the name from Jenkins Tools config
    }

    environment {
        DOCKERHUB_USERNAME = 'omarcy'
        APP_NAME = 'hmservice'
        IMAGE_NAME = "${DOCKERHUB_USERNAME}/${APP_NAME}:${env.BUILD_NUMBER}"
        CONTAINER_NAME = 'hotel-app'
        VOLUME_PATH = '/Users/omarCoding/springDemo/hotel_app_data'
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
                    usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo "${env.DOCKER_PASS}" | docker login -u "${env.DOCKER_USER}" --password-stdin
                    """

                    sh "docker push ${env.IMAGE_NAME}"
                    echo "Successfully pushed ${env.IMAGE_NAME} to Docker Hub."

                    sh "docker logout"
                    echo "Docker logout command executed."
                    }
                }
            }
        }

        stage('Deploy locally') {
            steps{
                script {
                    echo "Deploying image ${env.IMAGE_NAME} locally..."

                    sh "docker stop ${env.CONTAINER_NAME} || true"

                    sh "docker rm ${env.CONTAINER_NAME} || true"

                    sh """
                        docker run -d \
                        -p 8080:8080 \
                        -v ${env.VOLUME_PATH}:/app/data \
                        --name ${env.CONTAINER_NAME} \
                        ${env.IMAGE_NAME}
                    """
                    echo "Container ${env.CONTAINER_NAME} started with image ${env.IMAGE_NAME}."
                }
            }
        } // end of last stage
    }

    post {
        always { echo "Pipeline finished. Status: ${currentBuild.currentResult}" }
        success { echo 'Pipeline succeeded!' }
        failure { echo 'Pipeline failed!' }
    }
}