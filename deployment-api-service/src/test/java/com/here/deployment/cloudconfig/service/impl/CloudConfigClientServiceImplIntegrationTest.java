package com.here.deployment.cloudconfig.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.config.server.ConfigServerApplication;
import org.springframework.cloud.config.server.test.ConfigServerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigPortProvider;
import com.here.deployment.cloudconfig.service.impl.CloudConfigClientServiceImpl;
import com.here.deployment.domain.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ConfigServerApplication.class, DeploymentApiService.class },
	webEnvironment = WebEnvironment.RANDOM_PORT,
	properties = {
		"spring.cloud.config.server.prefix: /config",
		"spring.cloud.config.server.native.search-locations: file:./target/repos/config-repo/, file:./target/repos/config-repo/{application}"		
	}
)
@ActiveProfiles("native")
public class CloudConfigClientServiceImplIntegrationTest {	
	
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigClientServiceImplIntegrationTest.class);
	
	@LocalServerPort
	private int port;
	
	@Autowired
	CloudConfigClientServiceImpl cloudConfigClientService;
	
	@MockBean
	CloudConfigPortProvider cloudConfigPortProvider;
	
	static final String FAKE_APP_NAME = "FakeService";
	static final String FAKE_APP_ENV_DEV = "dev";
	static final String FAKE_APP_ENV_PROD = "prod";
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigServerTestUtils.prepareLocalRepo();
	}
	
	@Before
	public void setUp() {
		when(cloudConfigPortProvider.getPort()).thenReturn(port);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void fetchConfiguration_integration_dev_success() {
		// Do a dev and prod
		AppConfig<DcosDeploymentConfiguration> config = cloudConfigClientService.fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, 
				DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
				
		assertThat(config.getConfig().getMarathon().getUrl(), equalTo("http://global-test-url"));
		assertThat(config.getConfig().getMarathon().getAppId(), equalTo("/fakeservice-dev"));
		assertThat(config.getConfig().getMarathon().getCpus(), equalTo(1.0));
		assertThat(config.getConfig().getMarathon().getMem(), equalTo(512.0));
		assertThat(config.getConfig().getMarathon().getInstances(), equalTo(1));
		assertThat(config.getConfig().getMarathon().getContainer().getType(), equalTo("DOCKER"));
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getForcePullImage(), equalTo(true));
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getNetwork(), equalTo("BRIDGE"));
		assertThat(config.getConfig().getMarathon().getUpgradeStrategy().getMinimumHealthCapacity(), equalTo(1.0));
		assertThat(config.getConfig().getMarathon().getUpgradeStrategy().getMaximumOverCapacity(), equalTo(0.5));
		
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getPortMappings(), 
			hasItem( 
				allOf(
					hasProperty("containerPort", equalTo(8080)),
					hasProperty("hostPort", equalTo(0)),
					hasProperty("servicePort", equalTo(9000)),
					hasProperty("protocol", equalTo("tcp")),
					hasProperty("labels", hasItem(
							allOf(
								hasProperty("key", equalTo("app-label-test")),
								hasProperty("value", equalTo("app-label-test-value"))
							)
						)
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getContainer().getVolumes(), 
			hasItem( 
				allOf(
					hasProperty("containerPath", equalTo("/vol")),
					hasProperty("hostPath", equalTo("/vol")),
					hasProperty("mode", equalTo("RW")),
					hasProperty("id", equalTo("vol")),
					hasProperty("status", equalTo("status")),
					hasProperty("type", equalTo("type")),
					hasProperty("name", equalTo("name")),
					hasProperty("provider", equalTo("provider")),						
					hasProperty("options", hasItem(
							allOf(
								hasProperty("key", equalTo("app-vol-test")),
								hasProperty("value", equalTo("app-vol-test-value"))
							)
						)
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getLabels(), 
			allOf(
				hasItem( 
					allOf(
						hasProperty("key", equalTo("app-label-test")),
						hasProperty("value", equalTo("app-label-test-value"))	
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getHealthChecks(), 
			hasItem(
				allOf(
					hasProperty("protocol", equalTo("MESOS_HTTP")),
					hasProperty("portIndex", equalTo(0)),
					hasProperty("gracePeriodSeconds", equalTo(5)),
					hasProperty("path", equalTo("/")),
					hasProperty("intervalSeconds", equalTo(10)),
					hasProperty("timeoutSeconds", equalTo(10)),
					hasProperty("maxConsecutiveFailures", equalTo(3)),
					hasProperty("ignoreHttp1xx", equalTo(true))
				)
			)
		);

		assertThat(config.getConfig().getMarathon().getSecrets(),
			hasItem(
				allOf(
					hasProperty("name", equalTo("app-secret-test")),
					hasProperty("source", equalTo("app-secret-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getConstraints(),
			hasItem(
				allOf(
					hasProperty("attribute", equalTo("app-test-attribute")),
					hasProperty("operator", equalTo("UNIQUE")),
					hasProperty("value", equalTo("app-test-attribute-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getFetch(), 
			hasItem(
				allOf(
					hasProperty("uri", equalTo("app-test-fetch")),
					hasProperty("executable", equalTo(true)),
					hasProperty("extract", equalTo(true)),
					hasProperty("cache", equalTo(true)),
					hasProperty("outputFile", equalTo("outputFile"))
				)
			)
		);
		
		assertThat(config.getConfig().getGlobal().getEnv(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("global-env-test")),
					hasProperty("value", equalTo("global-env-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getGlobal().getDocker().getParameters(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("global-docker-param-test")),
					hasProperty("value", equalTo("global-docker-param-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getGlobal().getLabels(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("HAPROXY_0_BACKEND_HEAD")),
					hasProperty("template", equalTo("haproxy_backend_head")),
					hasProperty("args", hasItem(equalTo("300s")))
				)
			)
		);
		
		assertThat(config.getConfig().getApplication().getEnv(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("application-env-test")),
					hasProperty("value", equalTo("application-env-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getApplication().getDocker().getParameters(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("application-docker-param-test")),
					hasProperty("value", equalTo("application-docker-param-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getApplication().getLabels(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("app-label-test-list")),
					hasProperty("value", equalTo("app-label-test-list-value"))						
				)
			)
		);
		
		assertThat(config.getConfig().getEnvironment().getEnv(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("environment-env-test")),
					hasProperty("value", equalTo("environment-env-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getEnvironment().getDocker().getParameters(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("environment-docker-param-test")),
					hasProperty("value", equalTo("environment-docker-param-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getEnvironment().getLabels(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("HAPROXY_0_BACKEND_HTTP_HEALTHCHECK_OPTIONS")),
					hasProperty("template", equalTo("haproxy_backend_health")),
					hasProperty("args", hasItem(equalTo("myhost-dev.com")))
				)
			)
		);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void fetchConfiguration_integration_prod_success() {
		AppConfig<DcosDeploymentConfiguration> config = cloudConfigClientService.fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_PROD, 
				DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, DcosDeploymentConfiguration.class);
				
		assertThat(config.getConfig().getMarathon().getUrl(), equalTo("http://env-test-url"));
		assertThat(config.getConfig().getMarathon().getAppId(), equalTo("/fakeservice-prod"));
		assertThat(config.getConfig().getMarathon().getCpus(), equalTo(1.0));
		assertThat(config.getConfig().getMarathon().getMem(), equalTo(512.0));
		assertThat(config.getConfig().getMarathon().getInstances(), equalTo(3));
		assertThat(config.getConfig().getMarathon().getContainer().getType(), equalTo("DOCKER"));
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getForcePullImage(), equalTo(true));
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getNetwork(), equalTo("BRIDGE"));
		assertThat(config.getConfig().getMarathon().getUpgradeStrategy().getMinimumHealthCapacity(), equalTo(1.0));
		assertThat(config.getConfig().getMarathon().getUpgradeStrategy().getMaximumOverCapacity(), equalTo(0.5));
		
		assertThat(config.getConfig().getMarathon().getContainer().getDocker().getPortMappings(), 
			hasItem( 
				allOf(
					hasProperty("containerPort", equalTo(8080)),
					hasProperty("hostPort", equalTo(0)),
					hasProperty("servicePort", equalTo(9000)),
					hasProperty("protocol", equalTo("tcp")),
					hasProperty("labels", hasItem(
							allOf(
								hasProperty("key", equalTo("app-label-test")),
								hasProperty("value", equalTo("app-label-test-value"))
							)
						)
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getContainer().getVolumes(), 
			hasItem( 
				allOf(
					hasProperty("containerPath", equalTo("/vol")),
					hasProperty("hostPath", equalTo("/vol")),
					hasProperty("mode", equalTo("RW")),
					hasProperty("id", equalTo("vol")),
					hasProperty("status", equalTo("status")),
					hasProperty("type", equalTo("type")),
					hasProperty("name", equalTo("name")),
					hasProperty("provider", equalTo("provider")),						
					hasProperty("options", hasItem(
							allOf(
								hasProperty("key", equalTo("app-vol-test")),
								hasProperty("value", equalTo("app-vol-test-value"))
							)
						)
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getLabels(), 
			allOf(
				hasItem( 
					allOf(
						hasProperty("key", equalTo("app-label-test")),
						hasProperty("value", equalTo("app-label-test-value"))	
					)
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getHealthChecks(), 
			hasItem(
				allOf(
					hasProperty("protocol", equalTo("MESOS_HTTP")),
					hasProperty("portIndex", equalTo(0)),
					hasProperty("gracePeriodSeconds", equalTo(5)),
					hasProperty("path", equalTo("/")),
					hasProperty("intervalSeconds", equalTo(10)),
					hasProperty("timeoutSeconds", equalTo(10)),
					hasProperty("maxConsecutiveFailures", equalTo(3)),
					hasProperty("ignoreHttp1xx", equalTo(true))
				)
			)
		);

		assertThat(config.getConfig().getMarathon().getSecrets(),
			hasItem(
				allOf(
					hasProperty("name", equalTo("app-secret-test")),
					hasProperty("source", equalTo("app-secret-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getConstraints(),
			hasItem(
				allOf(
					hasProperty("attribute", equalTo("app-test-attribute")),
					hasProperty("operator", equalTo("UNIQUE")),
					hasProperty("value", equalTo("app-test-attribute-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getMarathon().getFetch(), 
			hasItem(
				allOf(
					hasProperty("uri", equalTo("app-test-fetch")),
					hasProperty("executable", equalTo(true)),
					hasProperty("extract", equalTo(true)),
					hasProperty("cache", equalTo(true)),
					hasProperty("outputFile", equalTo("outputFile"))
				)
			)
		);
		
		assertThat(config.getConfig().getGlobal().getEnv(), is(nullValue()));		
		assertThat(config.getConfig().getGlobal().getDocker().getParameters(), is(nullValue()));
		assertThat(config.getConfig().getGlobal().getLabels(), is(nullValue()));		
		assertThat(config.getConfig().getApplication().getEnv(), is(nullValue()));	
		assertThat(config.getConfig().getApplication().getDocker().getParameters(), is(nullValue()));
		assertThat(config.getConfig().getApplication().getLabels(), is(nullValue()));	
		
		assertThat(config.getConfig().getEnvironment().getEnv(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("environment-prod-env-test")),
					hasProperty("value", equalTo("environment-prod-env-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getEnvironment().getDocker().getParameters(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("environment-prod-docker-param-test")),
					hasProperty("value", equalTo("environment-prod-docker-param-test-value"))
				)
			)
		);
		
		assertThat(config.getConfig().getEnvironment().getLabels(),
			hasItem(
				allOf(
					hasProperty("key", equalTo("HAPROXY_0_BACKEND_HTTP_HEALTHCHECK_OPTIONS")),
					hasProperty("template", equalTo("haproxy_backend_health")),
					hasProperty("args", hasItem(equalTo("myhost-prod.com")))
				)
			)
		);
	}
}

