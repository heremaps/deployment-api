package com.here.deployment.cloudconfig.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigMappingService;
import com.here.deployment.cloudconfig.service.impl.CloudConfigHelperServiceImpl;
import com.here.deployment.domain.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class CloudConfigHelperServiceImplTest {	
	
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigHelperServiceImplTest.class);

	@SpyBean
	CloudConfigHelperServiceImpl cloudConfigHelperService;

	@MockBean
	RestOperations restOperations;
	
	@MockBean
	CloudConfigMappingService cloudConfigMappingService;
	
	@MockBean
	ResponseEntity<Environment> responseEntity;
	
	@MockBean
	Environment environment;
	
	@MockBean
	Properties properties;
	
	@MockBean
	AppConfig<DcosDeploymentConfiguration> appConfig;
	
	@MockBean(name = "propertySourceA")
	PropertySource propertySourceA;
	
	@MockBean(name = "propertySourceB")
	PropertySource propertySourceB;
	
	@Autowired
	List<PropertySource> propertySources;

	static final String SERVICE_ROOT = "http://localhost:0/config";
	static final String APP_NAME = "appName";
	static final String APP_ENV = "appEnv";	
	static final HttpEntity<Void> HTTP_ENTITY = new HttpEntity<>((Void) null, new HttpHeaders());
	
	static final String PROPERTY_SOURCE_A_NAME = "sourceA";
	static final String PROPERTY_SOURCE_A_KEY = "key1";
	static final String PROPERTY_SOURCE_A_VAL = "val1";
	static final String PROPERTY_SOURCE_B_NAME = "sourceB";
	static final String PROPERTY_SOURCE_B_KEY = "key2";
	static final String PROPERTY_SOURCE_B_VAL = "val2";
	static Map<String, Object> SOURCE_MAP_A;
	static Map<String, Object> SOURCE_MAP_B;
	static {
		Map<String, Object> propertySources = new HashMap<>();
		propertySources.put(PROPERTY_SOURCE_A_KEY, PROPERTY_SOURCE_A_VAL);
		SOURCE_MAP_A = Collections.unmodifiableMap(propertySources);				
	}
	static {
		Map<String, Object> propertySources = new HashMap<>();
		propertySources.put(PROPERTY_SOURCE_B_KEY, PROPERTY_SOURCE_B_VAL);
		SOURCE_MAP_B = Collections.unmodifiableMap(propertySources);				
	}
	
	
	@Before
	public void setup() throws Exception {
		when(restOperations.exchange(anyString(), any(HttpMethod.class), Matchers.<HttpEntity<Void>>any(), Matchers.<Class<Environment>>any())).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(environment);
		when(propertySourceA.getName()).thenReturn(PROPERTY_SOURCE_A_NAME);
		when(propertySourceB.getName()).thenReturn(PROPERTY_SOURCE_B_NAME);
		Mockito.<Map<?, ? extends Object>>when(propertySourceA.getSource()).thenReturn(SOURCE_MAP_A);
		Mockito.<Map<?, ? extends Object>>when(propertySourceB.getSource()).thenReturn(SOURCE_MAP_B);
	}
	
	@Test
	public void fetchEnvironment_success() {
		Environment environment = cloudConfigHelperService.fetchEnvironment(SERVICE_ROOT, APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME);
		
		assertThat(environment, not(nullValue()));
		verify(restOperations).exchange(StringUtils.join(SERVICE_ROOT, "/", APP_NAME, "/", CloudConfigHelperServiceImpl.GLOBAL_PROFILE, "," ,
				DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, ",",
				DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, "-", APP_ENV),
				HttpMethod.GET,
				HTTP_ENTITY, Environment.class);
	}
	
	@Test
	public void fetchEnvironment_httpClientErrorException() {
		when(restOperations.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<Void>>any(), Matchers.<Class<Environment>>any()))
			.thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
		
		try {
			cloudConfigHelperService.fetchEnvironment(SERVICE_ROOT, APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME);
		} catch (CloudConfigClientException cloudConfigClientException) {
			assertThat(cloudConfigClientException.getMessage(), not(nullValue()));
			assertThat(cloudConfigClientException.getStatusCode(), equalTo(HttpStatus.SERVICE_UNAVAILABLE.value()));
		}		
	}

	@Test
	public void convertEnvironmentToAppConfig_success() {
		doReturn(properties).when(cloudConfigHelperService).convertEnvironmentToProperties(any(Environment.class));
		doNothing().when(cloudConfigHelperService).processProperties(any(Properties.class));
		doReturn(appConfig).when(cloudConfigMappingService).convertPropertiesToAppConfig(any(Properties.class), Matchers.<Class<DcosDeploymentConfiguration>>any());
		
		cloudConfigHelperService.convertEnvironmentToAppConfig(environment, DcosDeploymentConfiguration.class);
		
		verify(cloudConfigHelperService).convertEnvironmentToProperties(environment);		
		verify(cloudConfigMappingService).convertPropertiesToAppConfig(properties, DcosDeploymentConfiguration.class);
		
		final ArgumentCaptor<Properties> propertiesCaptor = ArgumentCaptor.forClass(Properties.class);
		verify(cloudConfigHelperService).processProperties(propertiesCaptor.capture());
		assertThat(propertiesCaptor.getValue(), equalTo(properties));
	}

	@Test
	public void convertEnvironmentToProperties_success() {
		when(environment.getPropertySources()).thenReturn(propertySources);

		Properties returnProperties = cloudConfigHelperService.convertEnvironmentToProperties(environment);
		
		Properties compareProperties = new Properties();
		compareProperties.put(PROPERTY_SOURCE_A_KEY, PROPERTY_SOURCE_A_VAL);
		compareProperties.put(PROPERTY_SOURCE_B_KEY, PROPERTY_SOURCE_B_VAL);
		assertThat(returnProperties, not(nullValue()));
		assertThat(returnProperties, equalTo(compareProperties));
	}
	
	@Test
	public void convertEnvironmentToProperties_noPropertySources() {
		when(environment.getPropertySources()).thenReturn(null);

		Properties returnProperties = cloudConfigHelperService.convertEnvironmentToProperties(environment);
		
		Properties compareProperties = new Properties();
		assertThat(returnProperties, not(nullValue()));
		assertThat(returnProperties, equalTo(compareProperties));
	}
	
	@Test
	public void processProperties_success() {
		Properties returnProperties = new Properties();
		returnProperties.put(PROPERTY_SOURCE_A_KEY, PROPERTY_SOURCE_A_VAL);
		returnProperties.put(PROPERTY_SOURCE_B_KEY, PROPERTY_SOURCE_B_VAL);
		returnProperties.put("global.env", "value");
		returnProperties.put("global.docker.parameters", "value");
		returnProperties.put("application.env", "value");
		returnProperties.put("application.docker.parameters", "value");
		returnProperties.put("global.env[0].key", "key1");
		returnProperties.put("global.env[0].value", "val2");
		returnProperties.put("global.env[1].key", "key2");
		returnProperties.put("global.env[1].value", "val2");
		returnProperties.put("global.docker.parameters[0].key", "key1");
		returnProperties.put("global.docker.parameters[0].value", "val2");
		returnProperties.put("global.docker.parameters[1].key", "key2");
		returnProperties.put("global.docker.parameters[1].value", "val2");
		returnProperties.put("application.env[0].key", "key1");
		returnProperties.put("application.env[0].value", "val2");
		returnProperties.put("application.env[1].key", "key2");
		returnProperties.put("application.env[1].value", "val2");
		returnProperties.put("application.docker.parameters[0].key", "key1");
		returnProperties.put("application.docker.parameters[0].value", "val2");
		returnProperties.put("application.docker.parameters[1].key", "key2");
		returnProperties.put("application.docker.parameters[1].value", "val2");
		
		cloudConfigHelperService.processProperties(returnProperties);
		
		Properties compareProperties = new Properties();
		compareProperties.put(PROPERTY_SOURCE_A_KEY, PROPERTY_SOURCE_A_VAL);
		compareProperties.put(PROPERTY_SOURCE_B_KEY, PROPERTY_SOURCE_B_VAL);		
		assertThat(returnProperties, not(nullValue()));
		assertThat(returnProperties, equalTo(compareProperties));
	}
}

