pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "outcome:latest"
        JACOCO_DIR = "target/site/jacoco"
    }
    triggers {
        githubPullRequest()
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE .'
                }
            }
        }

        stage('Run Application in Docker') {
            steps {
                script {
                    sh 'docker run -d -p 8181:8181 --name outcome_app $DOCKER_IMAGE'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    sh 'docker run --rm -v $(pwd):/app -w /app maven:3.8.5-openjdk-17 mvn test'
                }
            }
        }

        stage('Run Smoke Tests') {
            steps {
                script {
                    sh 'docker run --rm -v $(pwd):/app -w /app maven:3.8.5-openjdk-17 mvn verify -Dtest=*/smoke/*'
                }
            }
        }

        stage('Generate JaCoCo Report') {
            steps {
                script {
                    sh 'docker run --rm -v $(pwd):/app -w /app maven:3.8.5-openjdk-17 ./mvnw jacoco:report'
                }
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "$JACOCO_REPORT_DIR",
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage Report'
                ])
            }
        }
    }

    post {
        always {
            script {
                sh 'docker stop outcome_app || true'
                sh 'docker rm outcome_app || true'
            }
        }
    }
}