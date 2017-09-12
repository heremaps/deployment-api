package com.here.deployment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.domain.PluginResponse;

@RunWith(JUnit4.class)
public class DeploymentResponseSuccessTest {

	DeploymentResponseSuccess deploymentResponseSuccess;

	@Before
	public void setUp() {		
		deploymentResponseSuccess = new DeploymentResponseSuccess();

	}
	
	@Test
	public void test_default_constructor() {
		assertThat(deploymentResponseSuccess, not(nullValue()));
		
		deploymentResponseSuccess.setAppId("appId");
		
		assertThat(deploymentResponseSuccess.getAppId(), equalTo("appId"));		
	}
	
	@Test
	public void test_appId_constructor() {
		deploymentResponseSuccess = new DeploymentResponseSuccess("appId");
		assertThat(deploymentResponseSuccess.getAppId(), equalTo("appId"));		
	}
	
	@Test
	public void test_addPluginReponse() {		
		assertThat(deploymentResponseSuccess.getPluginResponses(), nullValue());
		deploymentResponseSuccess.addPluginResponse(new PluginResponse("test1") {});
		assertThat(deploymentResponseSuccess.getPluginResponses(), not(nullValue()));
		assertThat(deploymentResponseSuccess.getPluginResponses(), hasSize(1));
		deploymentResponseSuccess.addPluginResponse(new PluginResponse("test2") {});
		assertThat(deploymentResponseSuccess.getPluginResponses(), hasSize(2));
	}
	
}
