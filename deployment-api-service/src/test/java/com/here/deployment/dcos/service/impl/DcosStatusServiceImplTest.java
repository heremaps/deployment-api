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
import com.here.deployment.dcos.service.DcosAppStatusService;
import com.here.deployment.dcos.service.impl.DcosStatusServiceImpl;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;
import mesosphere.dcos.client.exception.DcosException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DcosStatusServiceImplTest {	
	
	@Autowired
	DcosStatusServiceImpl dcosStatusService;		

	@MockBean
	CloudConfigClientService cloudConfigClientService;
	
	@MockBean
	DcosAppStatusService dcosAppStatusService;

	@MockBean
	AppStatusRequest appStatusRequest;
	
	@MockBean
	AppConfig<DcosDeploymentConfiguration> appConfig;
	
	@MockBean
	DcosDeploymentConfiguration dcosDeploymentConfiguration;
	
	@MockBean
	AppStatusResponseSuccess appStatusResponseSuccess;
	
	static final String APP_NAME = "appName";
	static final String APP_ENV = "appEnv";	
	
	@Before
	public void setup() throws Exception {
		when(appConfig.getConfig()).thenReturn(dcosDeploymentConfiguration);
		when(appStatusRequest.getAppName()).thenReturn(APP_NAME);
		when(appStatusRequest.getAppEnv()).thenReturn(APP_ENV);
	}
	
	@Test
	public void status_success() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(appStatusResponseSuccess);
		
		AppStatusResponseSuccess response = dcosStatusService.status(appStatusRequest);
		
		assertThat(response, not(nullValue()));			
		verify(cloudConfigClientService).fetchConfiguration(APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
		verify(dcosAppStatusService).appStatus(appStatusRequest, dcosDeploymentConfiguration);
	}
	
	@Test(expected = NotFoundException.class)
	public void status_cloudConfig_notFound() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenThrow(new CloudConfigClientException("error!", HttpStatus.NOT_FOUND.value()));
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(appStatusResponseSuccess);
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
	
	@Test(expected = ServiceException.class)
	public void status_cloudConfig_serviceException() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenThrow(new CloudConfigClientException());
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class))).thenReturn(appStatusResponseSuccess);
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
		
	@Test(expected = NotFoundException.class)
	public void status_dcos_notFound() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.NOT_FOUND.value(), "error!"));
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
	
	@Test(expected = NotAuthenticatedException.class)
	public void status_dcos_notAuthenticated() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.UNAUTHORIZED.value(), "error!"));
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void status_dcos_notAuthorized() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.FORBIDDEN.value(), "error!"));
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
	
	@Test(expected = BadRequestException.class)
	public void status_dcos_badRequest() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.BAD_REQUEST.value(), "error!"));
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
	
	@Test(expected = ServiceException.class)
	public void status_dcos_serviceException() throws Exception {				
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<DcosDeploymentConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(dcosAppStatusService.appStatus(any(AppStatusRequest.class), any(DcosDeploymentConfiguration.class)))
			.thenThrow(new DcosException(HttpStatus.SERVICE_UNAVAILABLE.value(), "error!"));
		
		dcosStatusService.status(appStatusRequest);
		
		fail();	
	}
}

