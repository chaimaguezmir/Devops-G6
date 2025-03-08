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


        stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=InstructorServicesImplTest clean test '
             }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
                stage(' Deploy') {
            steps {
                 sh 'mvn deploy -Dmaven.test.skip=true '
             }
        }
        
//         stage('Nexus') {
//             steps {
//         withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'admin', passwordVariable: 'anas1920')]) {
//             sh 'mvn deploy -DaltDeploymentRepository=deploymentRepo::default::http://172.20.116.17:8081/repository/maven-releases/ -Dusername=$admin -Dpassword=$anas1920'
//         }
//     }
// }
    }
}
