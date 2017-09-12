package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.mapper.DeploymentConfigToAppMapper;
import com.here.deployment.service.impl.ViewConfigRequestHandlerServiceImpl;
import mesosphere.dcos.client.model.v2.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class ViewConfigRequestHandlerServiceImplTest {	
	
	@Autowired
	ViewConfigRequestHandlerServiceImpl viewConfigRequestHandlerService;		

	@MockBean
	CloudConfigClientService cloudConfigClientService;
	
	@MockBean
	DeploymentConfigToAppMapper deploymentConfigToAppMapper;
	
	@MockBean
	DcosDeploymentConfiguration dcosDeploymentConfiguration;
	
	@MockBean
	AppConfig<DcosDeploymentConfiguration> appConfig;
	
	@MockBean
	App app;
	
	static final String APP_NAME = "appName";
	static final String APP_ENV = "appEnv";	
	
	@Before
	public void setup() throws Exception {
		when(appConfig.getConfig()).thenReturn(dcosDeploymentConfiguration);
	}

	@Test
	public void handleViewConfigRequest_success() {	
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(deploymentConfigToAppMapper.convert(any(DcosDeploymentConfiguration.class))).thenReturn(app);
	
		ResponseEntity<String> responseEntity = viewConfigRequestHandlerService.handleViewConfigRequest(APP_NAME, APP_ENV);
		assertThat(responseEntity, not(nullValue()));
		assertThat(responseEntity.getBody(), not(nullValue()));
		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
		verify(cloudConfigClientService).fetchConfiguration(APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
		verify(deploymentConfigToAppMapper).convert(dcosDeploymentConfiguration);
	}
	
	@Test
	public void handleViewConfigRequest_cloud_config_exception() {	
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenThrow(new RuntimeException("error!"));
		
		ResponseEntity<String> responseEntity = viewConfigRequestHandlerService.handleViewConfigRequest("appName", "appEnv");
		
		assertThat(responseEntity, not(nullValue()));
		assertThat(responseEntity.getBody(), not(nullValue()));
		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@Test
	public void handleViewConfigRequest_mapper_exception() {
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(deploymentConfigToAppMapper.convert(any(DcosDeploymentConfiguration.class))).thenThrow(new RuntimeException("error!"));
	
		ResponseEntity<String> responseEntity = viewConfigRequestHandlerService.handleViewConfigRequest("appName", "appEnv");
		assertThat(responseEntity, not(nullValue()));
		assertThat(responseEntity.getBody(), not(nullValue()));
		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
	}
}

