pipeline {
    agent{
        docker {
            image 'maven:3.8.7-eclipse-temurin-17-alpine' 
        }
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Run Scheduler Simulation') {
            steps {
                script {
                    sh '''
                    mvn exec:java -Dexec.mainClass="co.edu.icesi.dev.outcome_curr_mgmt.service.management.scheduler.UserServiceScheduler"
                    '''

                }
            }
        }
    }
    triggers {
        cron('H/15 * * * *') // Ejecutar el pipeline cada 15 minutos
    }
}
