pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    environment {
        // Exemple de variable d'environnement
        MY_VARIABLE = 'value'
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
