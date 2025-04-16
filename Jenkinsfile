pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        ARTIFACT = "gestion-station-ski"
        DOCKER_IMAGE = "ahlemtrabelsi/${ARTIFACT}:1.0.0"
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_LOGIN = 'squ_be5192562c66cb09687b3d1bfc987596789924b6'
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

       stage('📤 Deploy to Nexus') {
    steps {
        nexusArtifactUploader(
            nexusVersion: 'nexus3',
            protocol: 'http',
            nexusUrl: 'localhost:8081',
            groupId: 'tn.esprit.spring',
            version: '1.2.2',
            repository: 'maven-releases',
            credentialsId: 'nexus-credentials',
            artifacts: [
                [artifactId: 'gestion-station-ski',
                 classifier: '',
                 file: 'target/gestion-station-ski-1.2.2.jar',
                 type: 'jar']
            ]
        )
    }
}

       stage('📤 Deploy to Nexus') {
         steps {
             sh 'mvn deploy -s settings.xml -Dmaven.test.skip=true'
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
                sh 'curl -s http://172.27.106.47:8080/prometheus || echo "Jenkins Prometheus non accessible"'
                sh 'curl -s http://localhost:9090/api/v1/targets || echo "Prometheus non accessible"'
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
