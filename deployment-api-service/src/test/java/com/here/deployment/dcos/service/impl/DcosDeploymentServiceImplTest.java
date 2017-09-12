package com.here.deployment.dcos.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.dcos.service.DcosAppCreationService;
import com.here.deployment.dcos.service.impl.DcosDeploymentServiceImpl;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;
import mesosphere.dcos.client.exception.DcosException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DcosDeploymentServiceImplTest {	
	
	@Autowired
	DcosDeploymentServiceImpl dcosDeploymentService;		

	@MockBean
	CloudConfigClientService cloudConfigClientService;
	
	@MockBean
	DcosAppCreationService dcosAppCreationService;

	@MockBean
	DeploymentRequest deploymentRequest;
	
	@MockBean
	AppConfig<DcosDeploymentConfiguration> appConfig;
	
	@MockBean
	DcosDeploymentConfiguration dcosDeploymentConfiguration;
	
	@MockBean
	DeploymentResponseSuccess deploymentResponseSuccess;
	
	static final String APP_NAME = "appName";
	static final String APP_ENV = "appEnv";	
	
	@Before
	public void setup() throws Exception {
		when(appConfig.getConfig()).thenReturn(dcosDeploymentConfiguration);
		when(deploymentRequest.getAppName()).thenReturn(APP_NAME);
		when(deploymentRequest.getAppEnv()).thenReturn(APP_ENV);
	}
	
	@Test
	public void deploy_success() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(deploymentResponseSuccess);
		
		DeploymentResponseSuccess response = dcosDeploymentService.deploy(deploymentRequest);
		
		assertThat(response, not(nullValue()));		
		verify(cloudConfigClientService).fetchConfiguration(APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
		verify(dcosAppCreationService).createApp(deploymentRequest, dcosDeploymentConfiguration);
	}
	
	@Test(expected = NotFoundException.class)
	public void deploy_cloudConfig_notFound() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenThrow(new CloudConfigClientException("error!", HttpStatus.NOT_FOUND.value()));
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(deploymentResponseSuccess);
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = ServiceException.class)
	public void deploy_cloudConfig_otherError() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenThrow(new CloudConfigClientException("error!"));
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(deploymentResponseSuccess);
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = NotFoundException.class)
	public void deploy_dcosError_notFound() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.NOT_FOUND.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = NotAuthenticatedException.class)
	public void deploy_dcosError_unauthorized() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.UNAUTHORIZED.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void deploy_dcosError_forbidden() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.FORBIDDEN.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	@Test(expected = ConflictException.class)
	public void deploy_dcosError_conflict() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.CONFLICT.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = BadRequestException.class)
	public void deploy_dcosError_badRequest() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.BAD_REQUEST.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = ServiceException.class)
	public void deploy_dcosError_otherError() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.SERVICE_UNAVAILABLE.value(), "error!"));
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	@Test(expected = BadRequestException.class)
	public void deploy_dcosError_npe() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppCreationService.createApp(any(DeploymentRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new NullPointerException());
		
		dcosDeploymentService.deploy(deploymentRequest);
		
		fail();	
	}
	
	
}

