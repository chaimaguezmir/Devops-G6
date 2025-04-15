pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        SONARQUBE_SERVER = 'sonarqube' // Name of the SonarQube server configured in Jenkins
        SONAR_TOKEN = credentials('sonarqube') // Jenkins Credential ID for the token
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                git branch: 'Course',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('Display Java and Maven Versions') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Clean Project') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile Project') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Test Project') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest test'
            }
        }
  stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh """
                        mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=your_project_key \
                            -Dsonar.host.url=$SONARQUBE_SERVER \
                            -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
