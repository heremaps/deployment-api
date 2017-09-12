#! /usr/bin/groovy
package com.here.jenkins

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * Thanks to https://incognitjoe.github.io/rest-calls-in-jenkins-pipelines.html
 */
@Grab("org.jodd:jodd-http:3.8.5")
import jodd.http.HttpRequest

class DeploymentApi {
	private static final String DEPLOY_REQUEST_PATH = '/v1/dcos/deploy'
	private static final String STATUS_REQUEST_PATH = '/v1/dcos/status'
	
    private HttpRequest httpRequest
    private String userAgent = 'Jenkins'		
	
    DeploymentApi() {
        httpRequest = new HttpRequest()
    }
    
	def deploy(String serviceUrl, String username, String password, String appName, String appEnv, String image) {
		def deployment = [
			"username":"${username}", 
			"password":"${password}", 
			"appName":"${appName}", 
			"appEnv":"${appEnv}", 
			"image":"${image}"
		]
				
		post(serviceUrl + DEPLOY_REQUEST_PATH, deployment)
		def result = blockUntilDeployedOrFailed(serviceUrl, username, password, appName, appEnv)
		if (result) {
			println "DC/OS deployment successful"
		} else {
			throw new RuntimeException("Deployment unsuccessful, please check your service state in DC/OS")
		}
	}
	
	def blockUntilDeployedOrFailed(String serviceUrl, String username, String password, String appName, String appEnv, int retryAttempts = 40, int sleepSeconds = 15) {
		def status = [
			"username":"${username}", 
			"password":"${password}", 
			"appName":"${appName}", 
			"appEnv":"${appEnv}"
		]
						
		int retries = 0	
	  	while(retries++ < retryAttempts) {
			def statusResponse = post(serviceUrl + STATUS_REQUEST_PATH, status);				
			def jsonSlurper = new JsonSlurper()
			def statusObject = jsonSlurper.parseText(statusResponse)
			if (statusObject.state == null || statusObject == 'failed') {
				throw new RuntimeException("Could not find status")			
			} else if (statusObject.state == 'deployed') {
				return true
			} else {
				sleep(sleepSeconds * 1000)
			}			
		}
		
		return false
	}
	
    def post(String url, Map<?, ?> body) {
        String jsonbody = new JsonBuilder(body).toString()
        def resp = httpRequest.post(url)
                .header("User-Agent", userAgent)
                .contentType('application/json')
                .body(jsonbody)
                .send()
		
		if (resp.statusCode != 200 && resp.statusCode != 201) {
			throw new RuntimeException(resp.bodyText())
		}                
        return resp.bodyText()
    }
}