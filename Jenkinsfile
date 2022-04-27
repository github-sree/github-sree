node{
  def appName="springapp"
    stage('CHECKOUT') {
          git branch: 'main', 
       credentialsId: 'github-sree',
       url: 'https://github.com/github-sree/github-sree.git'
  
   }
    stage('OPENSHIFT CHECKING'){
           openshift.withCluster('oc-cluster','oc-credential-id') {
                    openshift.withProject() {
                        echo "Using project: ${openshift.project()}"
                    }
                }
    }
    stage('CLEAN PROJECT'){
          openshift.withCluster('oc-cluster','oc-credential-id') {
                    openshift.withProject() {
                    openshift.selector( 'all', [ app:appName ] ).delete()
                    // openshift.selector( 'routes', [ app:'nodejs-example' ] ).delete()
                    // openshift.selector( 'dc', [ app:'nodejs-example' ] ).delete()
                    // openshift.selector( 'is', [ app:'nodejs-example' ] ).delete()
                    // openshift.selector( 'bc', [ app:'nodejs-example' ] ).delete()
                    }
    }
    
}