pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
            SONAR_HOST_URL = 'http://localhost:9000/'
            SONAR_LOGIN = 'sqa_c515a1e9bdea143cc25ad34e935baf4f14a266be'
        }
    stages {
        stage('GIT') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'

            }
        }

        stage('Maven ') {
            steps {
                sh "java -version"
                sh "mvn -version"
            }
        }

        stage('MVN CLEAN') {
                    steps {
                        sh 'mvn clean'
                    }
                }

        stage('MVN COMPILE') {
                    steps {
                        sh 'mvn compile'
                    }
                }
         stage(' test Projet') {
            steps {
                 sh 'mvn -Dtest=CourseServicesImplTest clean test '
             }
        }
         stage('MVN SONARQUBE') {
                            steps {
                                sh 'mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN}'
                            }
                        }
            stage('MVN DEPLOY') {
              steps {
            sh 'mvn clean deploy -DskipTests'
    }
}
    }
}
