pipeline {
    agent any
    tools {
        maven 'M2_HOME'
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
                    repository: 'jenkins-releases',  
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
    }
}
