pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Chaima'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'RegitrationEntity',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git',
                    credentialsId: 'jenkins-github-token'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=RegistrationServicesImplTest clean test '
             }
        }

     stage('SonarQube') {
    steps {
        withSonarQubeEnv('sq1') {
            sh 'mvn sonar:sonar -Dsonar.login=squ_d510fa4e9c81d7f17f230991a7f5dcf19087afd1'
        }
    }
}


    

        }
    }
}
