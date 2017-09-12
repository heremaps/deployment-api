package com.here.deployment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.domain.DeploymentState;
import mesosphere.dcos.client.model.v2.App;

@RunWith(JUnit4.class)
public class AppStatusResponseSuccessTest {

	AppStatusResponseSuccess appStatusResponseSuccess;

	@Before
	public void setUp() {		
		appStatusResponseSuccess = new AppStatusResponseSuccess();
		appStatusResponseSuccess.setAppId("appId");
	}
	
	@Test
	public void test_app_constructor() {
		App app = new App();
		app.setId("appId");
		app.setTasksStaged(1);
		app.setTasksRunning(2);
		app.setTasksHealthy(3);
		app.setTasksUnhealthy(4);
		app.setDeployments(Arrays.asList(new App.Deployment()));
		
		appStatusResponseSuccess = new AppStatusResponseSuccess(app);
		assertThat(appStatusResponseSuccess.getAppId(), equalTo("appId"));
		assertThat(appStatusResponseSuccess.getTasksStaged(), equalTo(1));
		assertThat(appStatusResponseSuccess.getTasksRunning(), equalTo(2));
		assertThat(appStatusResponseSuccess.getTasksHealthy(), equalTo(3));
		assertThat(appStatusResponseSuccess.getTasksUnhealthy(), equalTo(4));
		assertThat(appStatusResponseSuccess.getDeployments(), hasSize(1));
		
	}
	
	@Test
	public void test_healthy1() {		
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(1);
		appStatusResponseSuccess.setTasksUnhealthy(0);
		appStatusResponseSuccess.setTasksStaged(0);
		appStatusResponseSuccess.setDeployments(null);
		
		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYED.getDisplay()));
	}
	
	@Test
	public void test_healthy2() {
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(1);
		appStatusResponseSuccess.setTasksUnhealthy(0);
		appStatusResponseSuccess.setTasksStaged(0);
		appStatusResponseSuccess.setDeployments(Collections.<App.Deployment>emptyList());
		
		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYED.getDisplay()));
	}
	
	@Test
	public void test_unhealthy1() {
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(2);
		appStatusResponseSuccess.setTasksUnhealthy(0);
		appStatusResponseSuccess.setTasksStaged(0);

		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYING.getDisplay()));
	}
	
	@Test
	public void test_unhealthy2() {
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(1);
		appStatusResponseSuccess.setTasksUnhealthy(1);
		appStatusResponseSuccess.setTasksStaged(0);

		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYING.getDisplay()));
	}
	
	@Test
	public void test_unhealthy3() {
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(1);
		appStatusResponseSuccess.setTasksUnhealthy(0);
		appStatusResponseSuccess.setTasksStaged(1);

		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYING.getDisplay()));
	}
	
	@Test
	public void test_unhealthy4() {
		appStatusResponseSuccess.setTasksRunning(1);
		appStatusResponseSuccess.setTasksHealthy(1);
		appStatusResponseSuccess.setTasksUnhealthy(0);
		appStatusResponseSuccess.setTasksStaged(0);
		appStatusResponseSuccess.setDeployments(Arrays.asList(new App.Deployment()));
		assertThat(appStatusResponseSuccess.getState(), equalTo(DeploymentState.DEPLOYING.getDisplay()));
	}
	
	@Test
	public void test_get_null_deployments() {		
		appStatusResponseSuccess.deployments = null;
		
		assertThat(appStatusResponseSuccess.getDeployments(), nullValue());
	}
}
