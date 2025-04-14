pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube' // Nom du serveur Sonar configuré dans Jenkins > Manage Jenkins > Configure System
        SONAR_TOKEN = credentials('SONAR_TOKEN') // ID du token Jenkins Credential
    }

    stages {
        stage('Cloner le dépôt GIT') {
            steps {
                git branch: 'Course',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('Afficher les versions Java et Maven') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Nettoyer le projet') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compiler le projet') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Tester le projet') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest test'
            }
        }
 stage('SonarQube') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
     

      

      
    }
}
