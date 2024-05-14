pipeline {
    agent any

    environment {
            M2_HOME = "/Users/assviridov/apache-maven-3.9.6"
            PATH = "${M2_HOME}/bin:${PATH}"
        }

    tools {
        maven 'my_maven'
    }

    stages {
        stage("Compile code") {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage("Tests") {
            when {
                branch 'feature/*'
            }
            steps {
                bat 'mvn test'
            }
        }
        stage("Static analyse") {
            when {
                branch 'develop'
            }
            steps {
                bat 'mvn checkstyle:check'
            }
        }
        stage("Report") {
            when {
                branch 'feature/*'
            }
            steps {
                junit testResults: '**/surefire-reports/*.xml'
                jacoco()
            }
        }
        stage("Install") {
            steps {
                bat 'mvn install'
            }
        }
        stage("Publish") {
            steps {
                bat 'copy "main\\target\\main-1.0-SNAPSHOT-jar-with-dependencies.jar" "C:\\main-1.0.jar"'
            }
        }
    }
}