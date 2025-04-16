pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'     // Nom configuré dans Jenkins > Global Tool Configuration
        maven 'M2_HOME'     // Nom configuré dans Jenkins > Global Tool Configuration
    }

    environment {
        VERSION = "1.2.2-SNAPSHOT"
        ARTIFACT = "gestion-station-ski"
        GROUP_ID = "tn.esprit.spring"
        DOCKER_IMAGE = "ahlemtrabelsi/${ARTIFACT}:1.0.0"
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_LOGIN = 'squ_be5192562c66cb09687b3d1bfc987596789924b6'
        NEXUS_URL = '172.27.106.47:8081'
        NEXUS_REPO = 'maven-snapshots'
        CREDENTIALS_ID = 'deploymentRepo'
    }

    stages {
        stage('📥 Clone Git Repository') {
            steps {
                git branch: 'Course', url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('⚙️ Display Java & Maven Versions') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('🧹 Clean Project') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('🔨 Compile Project') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('🧪 Run Tests') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest test'
            }
        }

       

        stage('📦 Package Project') {
            steps {
                sh 'mvn package -DskipTests'
                sh 'ls -lh target/'
            }
        }

        stage('🔍 SonarQube Analysis') {
            steps {
                sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=${ARTIFACT} \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_LOGIN}
                """
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('🐳 Docker Compose Deploy') {
            steps {
                script {
                    sh 'docker pull $DOCKER_IMAGE || true'
                    sh 'docker compose down || true'
                    sh 'docker compose up -d'
                }
            }
        }

        stage('🔎 Vérification des conteneurs') {
            steps {
                sh 'docker ps'
            }
        }

       stage('📈 Vérification Prometheus') {
    steps {
        script {
            echo '✅ Vérification de Jenkins Prometheus metrics...'
            sh 'curl -s http://172.27.106.47:8080/prometheus || echo "Jenkins Prometheus non accessible"'

            echo '✅ Vérification de Prometheus targets...'
            sh 'curl -s http://localhost:9090/api/v1/targets || echo "Prometheus non accessible"'
        }
    }
}

    } 

    post {
        success {
            archiveArtifacts artifacts: "target/*.jar", fingerprint: true
            echo '✅ Pipeline terminé avec succès.'
        }
        failure {
            echo '❌ Le pipeline a échoué.'
        }
    }
}
