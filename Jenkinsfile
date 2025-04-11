pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    environment {
        DOCKER_IMAGE = 'anasbettouzia/gestion-station-ski:1.0.0'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'Instructor',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'git-token'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Projet') {
            steps {
               sh 'mvn -Dtest=InstructorServicesImplTest clean test'
            }
        }
        
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
        
        stage('Deploy to Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: '172.20.116.17:8081',  
                    groupId: 'tn.esprit.spring',
                    version: '1.0',  
                    repository: 'maven-releases',  
                    credentialsId: 'deploymentRepo',  
                    artifacts: [
                        [
                            artifactId: 'gestion-station-ski',
                            classifier: '',
                            file: 'target/gestion-station-ski-1.0.jar',  
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

        // stage('Build & Push Docker Image') {
        //     steps {
        //         script {
        //             sh 'docker build -t $DOCKER_IMAGE .'
        //             sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        //             sh 'docker push $DOCKER_IMAGE'
        //         }
        //     }
        // }

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
    failure {
        script {
            def logExcerpt = currentBuild.rawBuild.getLog(50).join('\n')
            emailext(
                subject: "❌ Échec Pipeline : ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """Le pipeline a échoué.

Extrait des 50 dernières lignes :
---------------------------------
${logExcerpt}
---------------------------------

Consultez les logs complets ici : ${env.BUILD_URL}
""",
                to: 'abettouzia@gmail.com'
            )
        }
    }
}
}
