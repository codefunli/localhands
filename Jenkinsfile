pipeline {
	agent any

	//environment {
		//mavenHome = tool 'jenkins-maven'
	//}
	
	//node {
      //checkout scm
    //}

	
	tools {
		jdk 'JAVA_HOME'
		maven 'maven'
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
				sh 'mvn clean install'
			}
		}

		//stage('Test'){
			//steps{
				//bat "mvn test"
			//}
		//}

		stage('Deploy') {
			steps {
			    sh '''cd /var/lib/jenkins/workspace/localhand_pipeline/target
			    java -jar localhand-0.0.1-SNAPSHOT.jar'''
			}
		}
	}
}
