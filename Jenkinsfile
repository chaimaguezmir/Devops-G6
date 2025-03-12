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

        stage('Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'deploymentRepo', usernameVariable: 'admin', passwordVariable: 'admin123')]) {
                    sh '''
                        mvn -X clean deploy -Dmaven.test.skip=true \
                        -DrepositoryId=deploymentRepo \
                        -DaltDeploymentRepository=deploymentRepo::default::http://172.20.116.17:8081/repository/maven-releases/ \
                        -Dserver.username=$USERNAME \
                        -Dserver.password=$PASSWORD
                    '''
                }
            }
        }
    }
}
