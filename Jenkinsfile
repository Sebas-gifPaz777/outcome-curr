pipeline {
    agent any
    
      tools {
        maven 'local_mvn' // Nombre configurado en Global Tool Configuration
    }
    environment {
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
                    sh "mvn -Dtest=${TEST_CLASSES} test"
                }
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
