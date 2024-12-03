pipeline {
    agent any
    
      tools {
        maven 'local_mvn' // Nombre configurado en Global Tool Configuration
    }
    environment {
         DOCKER_IMAGE = "curriculum-app:latest"
        CONTAINER_NAME = "curriculum-app"
        DOCKER_PORT = "8081"
        HOST_PORT = "8081"
        GIT_REPO = "https://github.com/xkydev/pf_curriculum.git"
        GIT_CREDENTIALS_ID = 'github'
        TEST_CLASSES = 'co.edu.icesi.dev.outcome_curr_mgmt.service.management.UserServiceImplTest' // Clase o método de prueba específico
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Run Specific Tests') {
            steps {
                script {
                    dir('outcome-curr-mgmt'){
                         sh "mvn -Dtest=${TEST_CLASSES} test"
                     }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }
         stage('Run Multiple Docker Containers in Parallel') {
            parallel {
                stage('Run Instance 1') {
                    steps {
                        sh '''
                        docker stop ${CONTAINER_NAME}_1 || true
                        docker rm ${CONTAINER_NAME}_1 || true
                        docker run -d --name ${CONTAINER_NAME}_1 -p 8081:8081 -v /var/log/curriculum-app:/logs ${DOCKER_IMAGE}
                        '''
                    }
                }
                stage('Run Instance 2') {
                    steps {
                        sh '''
                        docker stop ${CONTAINER_NAME}_2 || true
                        docker rm ${CONTAINER_NAME}_2 || true
                        docker run -d --name ${CONTAINER_NAME}_2 -p 8082:8081 -v /var/log/curriculum-app:/logs ${DOCKER_IMAGE}
                        '''
                    }
                }
                stage('Run Instance 3') {
                    steps {
                        sh '''
                        docker stop ${CONTAINER_NAME}_3 || true
                        docker rm ${CONTAINER_NAME}_3 || true
                        docker run -d --name ${CONTAINER_NAME}_3 -p 8083:8081 -v /var/log/curriculum-app:/logs ${DOCKER_IMAGE}
                        '''
                    }
                }
            }
        }
    }
}
