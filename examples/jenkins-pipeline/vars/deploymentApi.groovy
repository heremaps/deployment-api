#!/usr/bin/env groovy
import com.here.jenkins.DeploymentApi

def call(String appName, String appEnv, String image, String serviceUrl) {
	
	def username
	def password
	
	withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'deployment-api',
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
        username = USERNAME                          				
		password = PASSWORD
	}
	
	DeploymentApi deploymentApi = new DeploymentApi()		
	def deploymentApiUrl = env.DEPLOYMENT_API_URL ?: serviceUrl
	deploymentApi.deploy(deploymentApiUrl, username, password, appName, appEnv, image)
	
	return appEnv	
}
