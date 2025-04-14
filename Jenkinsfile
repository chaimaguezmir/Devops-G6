pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube' // Nom du serveur Sonar configuré dans Jenkins
        SONAR_TOKEN = credentials('SONAR_TOKEN') // ID de ton token Jenkins Credential
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'Course',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('Maven Versions') {
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

        stage('Test Projet') {
            steps {
                sh 'mvn -Dtest=CourseServicesImplTest test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=mon-projet \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
    }
}
