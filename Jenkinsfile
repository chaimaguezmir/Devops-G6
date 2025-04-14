pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        
        DOCKER_IMAGE = "ahlemtrabelsi/gestion-station-ski:1.0.0"
        MAVEN_REPO_URL = "http://localhost:8081/repository/maven-releases/" // Nexus
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'Course',
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
     

       stage('SonarQube') {
             steps {
                    sh 'mvn sonar:sonar'
               //  withSonarQubeEnv('sq1') {
                 
                //}
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }

        stage('Déploiement Nexus') {
            steps {
                // Assurez-vous que settings.xml contient le serveur Nexus avec credentials
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Deploy avec Docker Compose') {
            steps {
                script {
                    sh "docker pull ${DOCKER_IMAGE}"
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
                    sh 'curl -s http://172.28.214.109:8080/prometheus || echo "Erreur: Jenkins ne fournit pas les métriques"'
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
