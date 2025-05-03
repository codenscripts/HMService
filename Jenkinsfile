// Jenkinsfile - Stable Version using 'agent any'

pipeline {
    agent any // Runs on the Jenkins controller (our custom image)

    tools { // Make Maven available in the PATH
        maven 'Maven 3.9' // Match the name from Jenkins Tools config
    }

    environment { // Define IMAGE_NAME using BUILD_NUMBER
        IMAGE_NAME = "hotel-management-app:${env.BUILD_NUMBER}"
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
                // mvn is in PATH via 'tools' directive
                sh 'mvn clean verify'
                echo 'Maven build & tests complete.'
            }
        }

        stage('Build Docker Image') {
            steps {
                // docker CLI is in PATH because we installed it in the custom Jenkins image
                // Jenkins user has permission via usermod in Dockerfile.jenkins
                sh "docker build -t ${env.IMAGE_NAME} ."
                echo "Docker image ${env.IMAGE_NAME} built."
            }
        }
    }

    post {
        always { echo "Pipeline finished. Status: ${currentBuild.currentResult}" }
        success { echo 'Pipeline succeeded!' }
        failure { echo 'Pipeline failed!' }
    }
}