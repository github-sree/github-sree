pipeline {
  agent any
  stages {
    stage('CHECKOUT') {
       git branch: 'main',
    credentialsId: 'github-sree',
    url: 'https://github.com/github-sree/github-sree.git'
    }
  }
}