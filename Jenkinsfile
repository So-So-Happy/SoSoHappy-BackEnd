def services = ['auth', 'feed', 'dm', 'notice']
pipeline {
    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-credential')
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and Deploy config-service') {
            steps {
                script {
                    dir('config-service') {
                        sh "java --version"
                        sh "chmod +x gradlew"
                        sh "./gradlew clean"
                        sh "./gradlew build"
                        archiveArtifacts artifacts: "**/build/libs/*.jar", allowEmptyArchive: true
                        docker.withRegistry('https://registry.hub.docker.com', 'docker-credential'){
                            dockerImage = docker.build("liardance/config-service:latest")
                            dockerImage.push()     
                        }
                        sh "kubectl --kubeconfig=/var/lib/jenkins/workspace/config apply -f k8s-config-service.yaml"
                        sh "kubectl --kubeconfig=/var/lib/jenkins/workspace/config rollout restart deployment config-deployment"
                    }
                }
            }
        }

        stage('Waiting config pod running') {
            steps {
                sleep(time: 30, unit: 'SECONDS')
            }
        }

        stage('Build and Deploy Other Services') {
            steps {
                script {
                    for (def serv in services) {
                        dir("${serv}-service") {
                            sh "java --version"
                            sh "chmod +x gradlew"
                            sh "./gradlew clean"
                            sh "./gradlew build"
                            archiveArtifacts artifacts: "**/build/libs/*.jar", allowEmptyArchive: true
	                        docker.withRegistry('https://registry.hub.docker.com', 'docker-credential'){
                            	dockerImage = docker.build("liardance/${serv}-service:latest")
                            	dockerImage.push()     
                            }
                            sh "kubectl --kubeconfig=/var/lib/jenkins/workspace/config apply -f k8s-${serv}-service.yaml"
                            sh "kubectl --kubeconfig=/var/lib/jenkins/workspace/config rollout restart deployment ${serv}-deployment"
                        }
                    }
                }
            }
        }
    }
}