package com.here.deployment.dcos.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.here.deployment.dcos.service.impl.DcosAppStatusServiceImpl;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseSuccess;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.GetAppResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DcosAppStatusServiceImplTest {	
	
	@Autowired
	DcosAppStatusServiceImpl dcosAppStatusService;		

	@MockBean
	DcosClientService dcosClientService;
	
	@MockBean
	Dcos dcos;
	
	@MockBean
	Marathon marathon;

	@MockBean
	AppStatusRequest appStatusRequest;
	
	@MockBean
	DcosDeploymentConfiguration deploymentConfig;
	
	@MockBean
	GetAppResponse getAppResponse;	
	
	@MockBean
	App app;
	
	static final String APP_ID = "appId";
	static final String IMAGE = "image";	
	static final String URL = "url";
	static final String USERNAME = "username";
	static final String PASSWORD = "password";
	
	@Test
	public void appStatus_success() throws Exception {		
		when(marathon.getUrl()).thenReturn(URL);
		when(marathon.getAppId()).thenReturn(APP_ID);
		when(deploymentConfig.getMarathon()).thenReturn(marathon);		
		when(appStatusRequest.getUsername()).thenReturn(USERNAME);
		when(appStatusRequest.getPassword()).thenReturn(PASSWORD);
		
		when(dcosClientService.dcosClient(anyString(), anyString(), anyString())).thenReturn(dcos);				
		
		when(dcos.getApp(anyString())).thenReturn(getAppResponse);		
		
		when(app.getId()).thenReturn(APP_ID);
		when(getAppResponse.getApp()).thenReturn(app);
		
		AppStatusResponseSuccess appStatusResponseSuccess = dcosAppStatusService.appStatus(appStatusRequest, deploymentConfig);
		assertThat(appStatusResponseSuccess, not(nullValue()));
		assertThat(appStatusResponseSuccess.getAppId(), equalTo(APP_ID));
		verify(dcosClientService).dcosClient(URL, USERNAME, PASSWORD);
		verify(dcos).getApp(APP_ID);
	}
}

