
env.GIT_BRANCH = env.BRANCH_NAME


node {
   
   stage('Build and Test') {
        echo 'Prepare for ' + env.BRANCH_NAME
        checkout scm
        def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
        if (matcher) {
            echo "Building version ${matcher[0][1]}"
          }
        def mvnHome = tool 'maven-3.3.9'
        sh "${mvnHome}/bin/mvn -B test"
    }

   stage('Run acceptance tests') {
      echo 'Running acceptance tests'
      def mvnHome = tool 'maven-3.3.9'
      sh "${mvnHome}/bin/mvn -B verify"

      // publish JUnit results to Jenkins
      publishHTML(target: [reportDir:'target/site/serenity', reportFiles: 'index.html', reportName: 'JBehave BDD report'])
   }

   stage('Deployed to Server') {
        echo 'Deployed in dev env'
        sh "curl --upload-file target/hello-world-war-1.0.0.war http://admin:admin@172.25.0.2:8080/manager/text/deploy?path=/hello&update=true"
   }

}