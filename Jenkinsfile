
env.GIT_BRANCH = env.BRANCH_NAME

url = null

node {
   
   stage('Build and Test') {

        def originalV = version();
        def major = originalV[1];
        def minor = originalV[2];
        def patch = originalV[3];
        //def patch  = Integer.parseInt(originalV[3]) + 1;
        def v = "${major}.${minor}.${patch}"
        if (v) {
           echo "Building version ${v}"
           writeFile file: "src/main/resources/myconfig.properties", text: "servername=MyTest\nappversion=1.0.0"
        }

        echo 'Prepare for ' + env.BRANCH_NAME
        checkout scm
        def mvnHome = tool 'maven-3.3.9'
        sh "${mvnHome}/bin/mvn -B test"
    }

    stage('Load server properties') {
            // def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
            // if (matcher) {
            //    echo "Building version ${matcher[0][1]}"
            //}
            url = server();
            //echo "Load property ${url}"
    }

   stage('Run acceptance tests') {
      echo 'Running acceptance tests'
      def mvnHome = tool 'maven-3.3.9'
      sh "${mvnHome}/bin/mvn -B verify"

      // publish JUnit results to Jenkins
      publishHTML(target: [reportDir:'target/site/serenity', reportFiles: 'index.html', reportName: 'BDD report'])
   }

   stage('Data migration') {
       echo "Check database version"
       echo "Apply database update"
   }

   stage('Deployed to Server') {
        def ser = url [0]
        echo "Deployed in ${env.BRANCH_NAME} environment to server ser"
        sh "curl --upload-file target/hello-world-war-1.0.0.war http://admin:admin@${ser}:8080/manager/text/deploy?path=/hello&update=true"

        /*for (i in url) {
            echo "Deployed in ${env.BRANCH_NAME} environment to server ${i}"
            sh "curl --upload-file target/hello-world-war-1.0.0.war http://admin:admin@${i}:8080/manager/text/deploy?path=/hello&update=true"
        }*/

   }



}

def server() {
       def envb = env.BRANCH_NAME;
       def str = readFile('delivery/'+envb+'.properties')
       def sr = new StringReader(str)
       def props = new Properties()
       props.load(sr)

       def url = props.getProperty('server').split(";")
       url
       //def user = props.getProperty('user')
       //def pwd = props.getProperty('pwd')
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(\\d*)\\.(\\d*)\\.(\\d*)(-SNAPSHOT)*</version>'
    matcher ? matcher[0] : null
}