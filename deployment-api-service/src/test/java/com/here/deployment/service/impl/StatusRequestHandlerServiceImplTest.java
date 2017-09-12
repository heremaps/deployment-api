package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
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
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseFailure;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.service.StatusService;
import com.here.deployment.service.impl.StatusRequestHandlerServiceImpl;
import mesosphere.dcos.client.model.v2.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class StatusRequestHandlerServiceImplTest {	
	
	@Autowired
	StatusRequestHandlerServiceImpl statusRequestHandlerService;		
		
	@MockBean
	@Qualifier("dcosStatusService")
	StatusService dcosStatusService;
	
	@MockBean
	AppStatusRequest appStatusRequest;
	
	@MockBean
	AppStatusResponseSuccess appStatusResponseSuccess;
	
	@MockBean
	App app;
	
	@Test
	public void handleStatusRequest_success() {
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenReturn(appStatusResponseSuccess);
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseSuccess.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void handleStatusRequest_notFound() {	
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenThrow(new NotFoundException());
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseFailure.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void handleStatusRequest_notAuthenticated() {		
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenThrow(new NotAuthenticatedException());
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseFailure.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void handleStatusRequest_notAuthorized() {	
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenThrow(new NotAuthorizedException());
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseFailure.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
	}
	
	@Test
	public void handleStatusRequest_badRequest() {	
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenThrow(new BadRequestException());
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseFailure.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void handleStatusRequest_unknownException() {		
		when(dcosStatusService.status(any(AppStatusRequest.class))).thenThrow(new RuntimeException());
		
		ResponseEntity<?> appStatusResponseEntity = statusRequestHandlerService.handleStatusRequest(appStatusRequest);
		assertThat(appStatusResponseEntity.getBody(), instanceOf(AppStatusResponseFailure.class));
		assertThat(appStatusResponseEntity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
	}

}

