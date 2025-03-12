pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Anas'
            }
        }

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
                 sh 'mvn -Dtest=InstructorServicesImplTest clean test '
             }
        }

        stage('Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credential', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                sh 'mvn deploy -Dmaven.test.skip=true -DrepositoryId=deploymentRepo'

                }
            }
        }
    }
}
