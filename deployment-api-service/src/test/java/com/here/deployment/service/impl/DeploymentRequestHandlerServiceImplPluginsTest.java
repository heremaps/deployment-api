package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.domain.PluginResponse;
import com.here.deployment.service.DeploymentPluginService;
import com.here.deployment.service.DeploymentService;
import com.here.deployment.service.MessageService;
import com.here.deployment.service.impl.DeploymentRequestHandlerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DeploymentRequestHandlerServiceImplPluginsTest {	
	
	@Autowired
	DeploymentRequestHandlerServiceImpl deploymentRequestHandlerService;		
	
	@MockBean
	DeploymentPluginService deploymentPluginService;
	
	@MockBean
	MessageService messageService;
	
	@MockBean
	@Qualifier("dcosDeploymentService")
	DeploymentService deploymentService;
	
	@MockBean
	DeploymentRequest deploymentRequest;
	
	@Test
	public void handleDeploymentRequestWithPlugins_success() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenReturn(new DeploymentResponseSuccess());
		when(deploymentPluginService.deploy(any(DeploymentRequest.class))).thenReturn(new PluginResponse("TEST") { } );
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseSuccess.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(((DeploymentResponseSuccess) deploymentResponseEntity.getBody()).getPluginResponses(), not(nullValue()));
		assertThat(((DeploymentResponseSuccess) deploymentResponseEntity.getBody()).getPluginResponses(), hasSize(1));		
		verify(deploymentService).deploy(deploymentRequest);
	}
	
	@Test
	public void handleDeploymentRequestWithPlugins_nullResponse() {		
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenReturn(new DeploymentResponseSuccess());
		when(deploymentPluginService.deploy(any(DeploymentRequest.class))).thenReturn( null );
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseSuccess.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(((DeploymentResponseSuccess) deploymentResponseEntity.getBody()).getPluginResponses(), is(nullValue()));		
	}
	

}

