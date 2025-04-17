pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
     environment {
        DOCKER_IMAGE = "ahmedgharbi/gestion-station-ski:1.0.0"
    }


    stages {
        stage('Hello Test') {
            steps {
                echo 'Ahmed'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Piste',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'e108a34a-e481-4440-9aa8-b9b56068d9f7'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=PisteServicesImplTest clean test '
             }
        }
        
         stage('SonarQube') {
             steps {
                 withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn install -Dmaven.test.skip=true'
            }
        }
        stage('Start Docker Compose') {
            steps {
                script {
                    sh 'docker pull $DOCKER_IMAGE'
                    sh 'docker compose down || true' // Arrête l'ancienne version si elle tourne
                    sh 'docker compose up -d'       // Lance la nouvelle version
                }
            }
        }

        stage('Check Running Containers') {
            steps {
                script {
                    sh 'docker ps'
                }
            }
        }
    }
}

