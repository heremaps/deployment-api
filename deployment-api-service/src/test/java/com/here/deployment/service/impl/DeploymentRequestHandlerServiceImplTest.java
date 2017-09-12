package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
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
import com.here.deployment.domain.DeploymentResponseFailure;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;
import com.here.deployment.service.DeploymentService;
import com.here.deployment.service.MessageService;
import com.here.deployment.service.impl.DeploymentRequestHandlerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DeploymentRequestHandlerServiceImplTest {	
	
	@Autowired
	DeploymentRequestHandlerServiceImpl deploymentRequestHandlerService;		

	@MockBean
	MessageService messageService;
	
	@MockBean
	@Qualifier("dcosDeploymentService")
	DeploymentService deploymentService;
	
	@MockBean
	DeploymentRequest deploymentRequest;
	
	@MockBean
	DeploymentResponseSuccess deploymentResponseSuccess;
	
	@Test
	public void handleDeploymentRequest_success() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenReturn(deploymentResponseSuccess);
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseSuccess.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
		verify(deploymentService).deploy(deploymentRequest);
	}
	
	@Test
	public void handleDeploymentRequest_notFound() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new NotFoundException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void handleDeploymentRequest_notAuthenticated() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new NotAuthenticatedException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void handleDeploymentRequest_notAuthorized() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new NotAuthorizedException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
	}
	
	@Test
	public void handleDeploymentRequest_conflict() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new ConflictException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.CONFLICT));
	}
	
	@Test
	public void handleDeploymentRequest_badRequest() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new BadRequestException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void handleDeploymentRequest_serviceException() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new ServiceException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@Test
	public void handleDeploymentRequest_unpectedException() {
		when(deploymentService.deploy(any(DeploymentRequest.class))).thenThrow(new RuntimeException());
		
		ResponseEntity<?> deploymentResponseEntity = deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
		assertThat(deploymentResponseEntity.getBody(), instanceOf(DeploymentResponseFailure.class));
		assertThat(deploymentResponseEntity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
	}

}

