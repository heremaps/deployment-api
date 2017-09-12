package com.here.deployment.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.config.server.ConfigServerApplication;
import org.springframework.cloud.config.server.test.ConfigServerTestUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.service.CloudConfigPortProvider;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseFailure;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponse;
import com.here.deployment.domain.DeploymentResponseFailure;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.ValidationFailedException;
import com.here.deployment.service.BindingResultHandlerService;
import com.here.deployment.service.DeploymentRequestHandlerService;
import com.here.deployment.service.StatusRequestHandlerService;
import com.here.deployment.service.ViewConfigRequestHandlerService;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.App.Deployment;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = { ConfigServerApplication.class, UnitTestApplication.class }, 
	webEnvironment = WebEnvironment.RANDOM_PORT,
	properties = {
		"spring.cloud.config.server.prefix: /config",
		"spring.cloud.config.server.native.search-locations: file:./target/repos/config-repo/, file:./target/repos/config-repo/{application}"		
	}
)
@ActiveProfiles("native")
public class DcosDeploymentControllerTest {
	
	static final Logger LOGGER = LoggerFactory.getLogger(DcosDeploymentControllerTest.class);
	
	@LocalServerPort
	private int port;
	
	@Autowired
    TestRestTemplate restTemplate;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@SpyBean
	ViewConfigRequestHandlerService viewConfigRequestHandlerService;
	
	@SpyBean
	DeploymentRequestHandlerService deploymentRequestHandlerService;
	
	@SpyBean
	StatusRequestHandlerService statusRequestHandlerService;
	
	@MockBean
	CloudConfigPortProvider cloudConfigPortProvider;

	@MockBean
	BindingResultHandlerService bindingResultHandlerService;

	@MockBean
	Deployment deployment;
	
	static final String FAKE_APP_NAME = "FakeService";
	static final String FAKE_APP_ENV_DEV = "dev";
	static final String FAKE_APP_ENV_PROD = "prod";
	static final String DEPLOYMENT_ID = "deploymentId";
	static final String IMAGE = "image";	
	static final String USERNAME = "username";
	static final String PASSWORD = "password";
	static final Integer TASKS_STAGED = 1;
	static final Integer TASKS_RUNNING = 2;
	static final Integer TASKS_HEALTHY = 3;
	static final Integer TASKS_UNHEALTHY = 4;
	
	
	String viewConfigReferenceFile;
	DeploymentRequest deploymentRequest;
	AppStatusRequest appStatusRequest;
	App app;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigServerTestUtils.prepareLocalRepo();
	}
	
	@Before
	public void setUp() throws IOException {
		when(cloudConfigPortProvider.getPort()).thenReturn(port);
		Resource viewConfigReferenceResource = resourceLoader.getResource("classpath:fake_service_dev_test.json");
        viewConfigReferenceFile = Files.lines(viewConfigReferenceResource.getFile().toPath()).collect(Collectors.joining());
        
        deploymentRequest = new DeploymentRequest();
    	deploymentRequest.setAppName(FAKE_APP_NAME);
    	deploymentRequest.setAppEnv(FAKE_APP_ENV_DEV);
    	deploymentRequest.setImage(IMAGE);
    	deploymentRequest.setUsername(USERNAME);
    	deploymentRequest.setPassword(PASSWORD);
    	
    	appStatusRequest = new AppStatusRequest();
    	appStatusRequest.setAppName(FAKE_APP_NAME);
    	appStatusRequest.setAppEnv(FAKE_APP_ENV_DEV);
    	appStatusRequest.setUsername(USERNAME);
    	appStatusRequest.setPassword(PASSWORD);
    	
    	app = new App();
    	app.setId(DEPLOYMENT_ID);
    	app.setTasksStaged(TASKS_STAGED);
    	app.setTasksRunning(TASKS_RUNNING);
    	app.setTasksHealthy(TASKS_HEALTHY);
    	app.setTasksUnhealthy(TASKS_UNHEALTHY);
  
	}

    @Test
    public void test_viewConfig_unit() {
    	doReturn(new ResponseEntity<String>("Test", HttpStatus.OK)).when(viewConfigRequestHandlerService).handleViewConfigRequest("FakeService", "dev");
    	
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/dcos/config?appName={appName}&appEnv={appEnv}", String.class, FAKE_APP_NAME, FAKE_APP_ENV_DEV);
        
        assertThat(responseEntity.getStatusCode().value(), equalTo(200));
    }
    
    @Test
    public void test_viewConfig_integration() throws JSONException {    	       
    	doCallRealMethod().when(viewConfigRequestHandlerService).handleViewConfigRequest("FakeService", "dev");
    	
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/dcos/config?appName={appName}&appEnv={appEnv}", String.class, FAKE_APP_NAME, FAKE_APP_ENV_DEV);
        
        JSONAssert.assertEquals(viewConfigReferenceFile, responseEntity.getBody(), false);
        assertThat(responseEntity.getStatusCode().value(), equalTo(200));
    }

    @Test
    public void test_deploy_success() {
    	doNothing().when(bindingResultHandlerService).handleBindingResult(any(BindingResult.class));
    	doReturn(new ResponseEntity<DeploymentResponse>(new DeploymentResponseSuccess(DEPLOYMENT_ID), HttpStatus.CREATED))
    		.when(deploymentRequestHandlerService).handleDeploymentRequest(any(DeploymentRequest.class));
    	
    	ResponseEntity<DeploymentResponseSuccess> responseEntity = restTemplate.postForEntity("/v1/dcos/deploy", deploymentRequest, DeploymentResponseSuccess.class);
    	         
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));
        assertThat(responseEntity.getBody(), hasProperty("appId", equalTo(DEPLOYMENT_ID)));
    }
    
    @Test
    public void test_deploy_validationFailed() {
    	doThrow(new ValidationFailedException("Validation failed!")).when(bindingResultHandlerService).handleBindingResult(any(BindingResult.class));
    	ResponseEntity<DeploymentResponseFailure> responseEntity = restTemplate.postForEntity("/v1/dcos/deploy", deploymentRequest, DeploymentResponseFailure.class);
        
        assertThat(responseEntity.getStatusCode().value(), equalTo(422));
    }
    
    @Test
    public void test_status_success() {  		
    	doNothing().when(bindingResultHandlerService).handleBindingResult(any(BindingResult.class));
    	doReturn(new ResponseEntity<AppStatusResponseSuccess>(new AppStatusResponseSuccess(app), HttpStatus.OK))
    		.when(statusRequestHandlerService).handleStatusRequest(any(AppStatusRequest.class));
    	
    	ResponseEntity<AppStatusResponseSuccess> responseEntity = restTemplate.postForEntity("/v1/dcos/status", appStatusRequest, AppStatusResponseSuccess.class);
    	         
        assertThat(responseEntity.getStatusCode().value(), equalTo(200));
        assertThat(responseEntity.getBody(), hasProperty("appId", equalTo(DEPLOYMENT_ID)));
    }
    
    @Test
    public void test_status_validationFailed() {
    	ValidationFailedException validationFailedException = new ValidationFailedException("error!", Arrays.asList("Validation error!"));;
    	doThrow(validationFailedException).when(bindingResultHandlerService).handleBindingResult(any(BindingResult.class));
    	ResponseEntity<AppStatusResponseFailure> responseEntity = restTemplate.postForEntity("/v1/dcos/status", deploymentRequest, AppStatusResponseFailure.class);
        
        assertThat(responseEntity.getStatusCode().value(), equalTo(422));
    }
}

