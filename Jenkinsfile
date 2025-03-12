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
                    nexusUrl: '172.20.116.17:8081',  // Remplacez par l'URL de votre Nexus
                    groupId: 'tn.esprit.spring',
                    version: '1.0',  // Remplacez par la version de votre projet
                    repository: 'jenkins-releases',  // Remplacez par le nom de votre dépôt
                    credentialsId: 'deploymentRepo',  // Utilisez l'ID des informations d'identification configurées dans Jenkins
                    artifacts: [
                        [
                            artifactId: 'gestion-station-ski',
                            classifier: '',
                            file: 'target/gestion-station-ski-1.0.jar',  // Chemin vers le fichier JAR généré
                            type: 'jar'
                        ],
                        [
                            artifactId: 'gestion-station-ski',
                            classifier: '',
                            file: 'pom.xml',  // Déployez également le fichier POM
                            type: 'pom'
                        ]
                    ]
                )
            }
        }
    }
}
