pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Mahdi'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'SkierEntity',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'git-token'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=SkierServicesImplTest clean test '
             }
        }
                stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }

        stage('SonarQube') {
                    steps {
                        withSonarQubeEnv('sq1') {
                            sh 'mvn sonar:sonar'
                        }
                    }
         }
        stage('Deploy') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
        

    }
}
