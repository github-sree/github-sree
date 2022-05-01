node {
    stage('CHECKOUT') {
    git branch: 'dev',
       credentialsId: 'github-sree',
       url: 'https://github.com/github-sree/k8s-learning-backend.git'
    }
  stage('docker build & push') {
        // sh 'docker build -t sreedocker0798/k8s-learning-backend:1.0.0 .'
        // sh 'docker push sreedocker0798/k8s-learning-backend:1.0.0'
        docker.withRegistry('https://registry.hub.docker.com', 'docker-credential') {
      def customImage = docker.build('sreedocker0798/k8s-learning-backend:1.0.0')

        /* Push the container to the custom Registry */
      customImage.push()
        }
  }

    // stage('clean kubernetes deployment') {
    //     sh 'kubectl delete deploy k8s-frontend'
    //     sh 'kubectl delete svc k8s-frontend-service'
    // }

    stage('deploy to kubernetes') {
        try {
      sh 'kubectl delete deploy k8s-backend'
        }catch (Exception e) {
      echo 'deployment not present'
        }
    sh 'kubectl apply -f deploy-resources/k8s-backend-deployment.yaml'
    }
}
