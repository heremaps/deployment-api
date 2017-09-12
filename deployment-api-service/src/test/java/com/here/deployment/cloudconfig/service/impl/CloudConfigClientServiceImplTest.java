package com.here.deployment.cloudconfig.service.impl;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigHelperService;
import com.here.deployment.cloudconfig.service.CloudConfigPortProvider;
import com.here.deployment.cloudconfig.service.impl.CloudConfigClientServiceImpl;
import com.here.deployment.domain.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class CloudConfigClientServiceImplTest {	
	
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigClientServiceImplTest.class);
	
	@Autowired
	CloudConfigClientServiceImpl cloudConfigClientService;

	@MockBean
	CloudConfigHelperService cloudConfigHelperService;
	
	@MockBean
	CloudConfigPortProvider cloudConfigPortProvider;

	@MockBean
	Environment env;
	
	@MockBean
	AppConfig<DcosDeploymentConfiguration> config;
	
	static final String APP_NAME = "appName";
	static final String APP_ENV = "appEnv";
	
	@Test
	public void fetchConfiguration_success() {
		when(cloudConfigPortProvider.getPort()).thenReturn(0);
		when(cloudConfigHelperService.fetchEnvironment(anyString(), anyString(), anyString(), anyString())).thenReturn(env);
		when(cloudConfigHelperService.convertEnvironmentToAppConfig(any(Environment.class), Matchers.<Class<DcosDeploymentConfiguration>>any())).thenReturn(config);
				
		AppConfig<DcosDeploymentConfiguration> appConfig = cloudConfigClientService.fetchConfiguration(APP_NAME, APP_ENV, 
				DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
		
		assertThat(appConfig, not(nullValue()));
		verify(cloudConfigHelperService).fetchEnvironment(StringUtils.join(CloudConfigClientServiceImpl.CLOUD_CONFIG_SERVER_URL, ":0", CloudConfigClientServiceImpl.CLOUD_CONFIG_CONTEXT_ROOT), 
				APP_NAME, APP_ENV, DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME);
		verify(cloudConfigHelperService).convertEnvironmentToAppConfig(env, DcosDeploymentConfiguration.class);
	}

	
}

