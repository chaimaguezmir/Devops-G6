pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        SONARQUBE_SERVER = 'SonarQube' // nom de ton serveur Sonar configuré dans Jenkins (Manage Jenkins > Configure System)
        SONAR_TOKEN = credentials('SONAR_TOKEN') // récupère le token via ID
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'Course',
                    url: 'https://github.com/chaimaguezmir/Devops-G6.git'
            }
        }

        stage('Maven') {
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
                sh 'mvn -Dtest=CourseServicesImplTest clean test'
            }
        }


        stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh """
                        ./mvnw clean verify sonar:sonar \
                        -Dsonar.projectKey=mon-projet \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }
        stage('SonarQube') {
    steps {
        withSonarQubeEnv('sq1') {
            withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN}"
            }
        }
    }
}

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar'
                // Décommentez la ligne suivante si Sonar est configuré dans Jenkins
                // withSonarQubeEnv('sq1') {
                //     sh 'mvn sonar:sonar'
                // }
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
    } // ✅ fermeture de stages
} // ✅ fermeture du pipeline
