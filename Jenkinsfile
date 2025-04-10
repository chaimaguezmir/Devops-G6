pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    environment {
        DOCKER_IMAGE = "chaimaguezmir/gestion-station-ski:1.0.0"
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Chaima'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'RegitrationEntity',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'jenkins-github-token'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Projet') {
            steps {
                sh 'mvn -Dtest=RegistrationServicesImplTest clean test'
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
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

    

        stage('Start Docker Compose') {
            steps {
                script {
                      sh 'docker pull $DOCKER_IMAGE'
                    sh 'docker compose down || true' // Arrête l'ancienne version
                    sh 'docker compose up -d'       // Démarre la nouvelle version
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
         stage('Vérification Prometheus') {
            steps {
                script {
                    echo 'Vérification de l\'exposition des métriques de Jenkins'
                    sh 'curl -s http:http://172.21.55.213:8080/prometheus || echo "Erreur: Jenkins ne fournit pas les métriques"'
                    echo 'Vérification que Prometheus récupère les métriques'
                    sh 'curl -s http://localhost:9090/targets | jq .'
                }
            }
        }
    


    post {
        always {
            echo 'Pipeline terminé.'
        }
    }
}

