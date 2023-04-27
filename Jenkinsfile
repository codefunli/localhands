pipeline {
	agent {label 'linux'}

	//environment {
		//mavenHome = tool 'jenkins-maven'
	//}
	
	//node {
      //checkout scm
    //}

	
	tools {
		jdk 'jdk-17-oracle-x64'
	}
	//ws(LocalHands/LocalHand/localhand) {
		//echo "Change workspace to localhand"
	//}

	stages {
	
	    

		stage('Build'){
			steps {
			//def p = 'cd /var/lib/jenkins/workspace/localhand_pipeline/LocalHand/localhand'.execute() | 'mvn clean install -DskipTests'.execute()
			//p.waitFor()
			    //sh 'cd /var/lib/jenkins/workspace/localhand_pipeline/LocalHand/localhand'
				sh 'mvn install -DskipTests'
			}
		}

		//stage('Test'){
			//steps{
				//bat "mvn test"
			//}
		//}

		stage('Deploy') {
			steps {
			    sh 'mvn jar:jar deploy:deploy'
			}
		}
	}
}
