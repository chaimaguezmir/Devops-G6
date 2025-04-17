pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
     environment {
        DOCKER_IMAGE = "mahdikalfat/gestion-station-ski:1.0.0"
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Mahdi'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'SkierEntity',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'git-token'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=SkierServicesImplTest clean test '
             }
        }
                stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
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
        stage('Vérification Prometheus') {
            steps {
                script {
                    echo 'Vérification de l\'exposition des métriques de Jenkins'
                    sh 'curl -s http://192.168.122.169:8080/prometheus || echo "Erreur: Jenkins ne fournit pas les métriques"'
                    echo 'Vérification que Prometheus récupère les métriques'
                    sh 'curl -s http://localhost:9090/api/v1/targets | jq .'
                }
            }
        }
    }
}
