pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        DOCKER_IMAGE = "ahlemtrabelsi/gestion-station-ski:1.0.0"
        SONAR_HOST_URL = 'http://localhost:9000' // ✅ pas de slash à la fin
        SONAR_LOGIN = 'squ_be5192562c66cb09687b3d1bfc987596789924b6' // ⚠️ visible dans les logs Jenkins !
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                git branch: 'Course',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('Display Java and Maven Versions') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Clean Project') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile Project') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Test Project') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=your_project_key \
                    -Dsonar.host.url=${SONAR_HOST_URL} \
                    -Dsonar.login=${SONAR_LOGIN}
                """
            }
        }

         stage('Deploy to Nexus') {
                    steps {
                        sh 'ls -lh target/'
                        sh 'test -f target/gestion-station-ski-1.2.2.jar'
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: '172.27.106.47:8081',
                            groupId: 'tn.esprit.spring',
                            version: '1.2.2',
                            repository: 'maven-releases',
                            credentialsId: 'deploymentRepo',
                            artifacts: [
                                [
                                    artifactId: 'gestion-station-ski',
                                    classifier: '',
                                    file: 'target/gestion-station-ski-1.2.2.jar',
                                    type: 'jar'
                                ],
                                [
                                    artifactId: 'gestion-station-ski',
                                    classifier: '',
                                    file: 'pom.xml',
                                    type: 'pom'
                                ]
                            ]
                        )
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
       // stage('Deploy') {
         //   steps {
           //     sh 'mvn deploy -Dmaven.test.skip=true'
           // }
       // }
    }
}
