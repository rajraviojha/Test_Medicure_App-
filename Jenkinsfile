pipelines{
    agent any
    stages {
        stage('Code Checkout"') {
            steps {
                echo "Cloning the code"
                git url: "https://github.com/rajraviojha/Medicure_App.git", branch: "master"
               }
            }

        stage("Code build"){
            steps{
            echo "Building the code"
            sh'mvn clean package'
            }
        }

        stage("Build Docker Image"){
            steps{
            echo "Contrizied the code"
            sh 'docker build -t jharajltp/java-app:latest .'
            }
        } 

        stage("Code Push to repo") {
            steps {
                echo "Deploying to container"
                catchError(buildResult: 'UNSTABLE') {
                    sh "docker run -d -p 8082:8082 java-app:latest"
                    // Add a delay to allow the container to start up completely
                    sleep 30
                }
            }
        }

        stage("Selenium Test") {
            steps {
                echo "Running Selenium tests"
                sh 'java -jar Selenium.jar'
            }
        }


        }
    }