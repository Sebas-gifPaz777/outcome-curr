pipeline {
    agent any

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

