pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        SONAR_HOST_URL = 'http://localhost:9000/'
        SONAR_LOGIN = 'sqa_c515a1e9bdea143cc25ad34e935baf4f14a266be'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'Course', // Changez ceci si nécessaire
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }
        stage('Maven') {
            steps {
                sh "java -version"
                sh "mvn -version"
            }
        }
        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test Projet') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest clean test'
            }
        }
        
        stage('Deploy to Nexus') {
            steps {
                // Déployer le package dans Nexus
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
        
    
        stage('Deploy avec Docker Compose') {
            steps {
                script {
                    sh 'docker pull $DOCKER_IMAGE'
                    sh 'docker compose down || true'
                    sh 'docker compose up -d'
                }
            }
        }

        stage('Vérification des conteneurs') {
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
                    sh 'curl -s http://172.20.116.17:8080/prometheus || echo "Erreur: Jenkins ne fournit pas les métriques"'
                    echo 'Vérification que Prometheus récupère les métriques'
                    sh 'curl -s http://localhost:9090/api/v1/targets | jq .'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé.'
        }
    }
       
    }
}
