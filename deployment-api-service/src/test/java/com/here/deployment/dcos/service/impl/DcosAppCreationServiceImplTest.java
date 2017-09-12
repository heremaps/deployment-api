package com.here.deployment.dcos.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.model.dcos.Marathon;
import com.here.deployment.dcos.service.DcosClientService;
import com.here.deployment.dcos.service.impl.DcosAppCreationServiceImpl;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.mapper.DeploymentConfigToAppMapper;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.Container;
import mesosphere.dcos.client.model.v2.Deployment;
import mesosphere.dcos.client.model.v2.Docker;
import mesosphere.dcos.client.model.v2.UpdateAppResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DcosAppCreationServiceImplTest {	
	
	@Autowired
	DcosAppCreationServiceImpl dcosAppCreationService;		

	@MockBean
	DcosClientService dcosClientService;
	
	@MockBean
	DeploymentConfigToAppMapper deploymentConfigToAppMapper;
	
	@MockBean
	Dcos dcos;
	
	@MockBean
	Marathon marathon;

	@MockBean
	DeploymentRequest deploymentRequest;
	
	@MockBean
	DcosDeploymentConfiguration deploymentConfig;
	
	@MockBean
	UpdateAppResponse updateAppResponse;
	
	@MockBean
	App app;
	
	@MockBean
	Deployment deployment;

	@MockBean
	Container container;
	
	@MockBean
	Docker docker;
	
	static final String DEPLOYMENT_ID = "deploymentId";
	static final String IMAGE = "image";	
	static final String URL = "url";
	static final String USERNAME = "username";
	static final String PASSWORD = "password";
	
	@Before
	public void setup() throws Exception {		
		when(marathon.getUrl()).thenReturn(URL);
		when(deploymentConfig.getMarathon()).thenReturn(marathon);		
		when(deploymentRequest.getUsername()).thenReturn(USERNAME);
		when(deploymentRequest.getPassword()).thenReturn(PASSWORD);
		
		when(deploymentRequest.getImage()).thenReturn(IMAGE);
		when(updateAppResponse.getDeploymentId()).thenReturn(DEPLOYMENT_ID);
		when(deployment.getId()).thenReturn(DEPLOYMENT_ID);
		
		when(app.getId()).thenReturn(DEPLOYMENT_ID);
		when(app.getContainer()).thenReturn(container);
		when(container.getDocker()).thenReturn(docker);
		
		when(dcosClientService.dcosClient(anyString(), anyString(), anyString())).thenReturn(dcos);
		when(deploymentConfigToAppMapper.convert(any(DcosDeploymentConfiguration.class))).thenReturn(app);
		when(dcos.updateApp(anyString(), anyBoolean(), any(App.class))).thenReturn(updateAppResponse);
		when(dcos.getDeployments()).thenReturn(Arrays.asList(deployment));
	}
	
	@Test
	public void createApp_success() throws Exception {				
		DeploymentResponseSuccess deploymentResponseSuccess = dcosAppCreationService.createApp(deploymentRequest, deploymentConfig);		
		assertThat(deploymentResponseSuccess, not(nullValue()));
		assertThat(deploymentResponseSuccess.getAppId(), equalTo(DEPLOYMENT_ID));
		verify(dcosClientService).dcosClient(URL, USERNAME, PASSWORD);
		verify(deploymentConfigToAppMapper).convert(deploymentConfig);
		verify(dcos).updateApp(DEPLOYMENT_ID, false, app);
	}
	
	@Test(expected = ConflictException.class)
	public void createApp_deploymentNotReturned() throws Exception {
		when(updateAppResponse.getDeploymentId()).thenReturn(null);
		
		dcosAppCreationService.createApp(deploymentRequest, deploymentConfig);
		fail();
	}
	
	@Test(expected = ConflictException.class)
	public void createApp_noDeployments() throws Exception {
		when(dcos.getDeployments()).thenReturn(null);
		
		dcosAppCreationService.createApp(deploymentRequest, deploymentConfig);
		fail();
	}
	
	@Test(expected = ConflictException.class)
	public void createApp_deploymentNotInList() throws Exception {
		final String deploymentId2 = "deploymentId2";		
		when(deployment.getId()).thenReturn(deploymentId2);
		
		dcosAppCreationService.createApp(deploymentRequest, deploymentConfig);
		fail();
	}
}

