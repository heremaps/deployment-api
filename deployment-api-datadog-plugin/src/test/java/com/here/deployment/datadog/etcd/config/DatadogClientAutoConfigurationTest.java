package com.here.deployment.datadog.etcd.config;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import com.here.deployment.datadog.AutoConfigApplication;
import com.here.deployment.datadog.etcd.config.DatadogClientAutoConfiguration;
import com.here.deployment.datadog.etcd.service.DatadogEtcdClient;
import com.here.deployment.service.DeploymentPluginService;

@RunWith(SpringRunner.class)
public class DatadogClientAutoConfigurationTest {	

	ApplicationContext context;

	ConfigurableEnvironment environment = new StandardEnvironment();
	
	@Test
	public void contextLoads() throws Exception {		
		load(DatadogClientAutoConfiguration.class, "plugins.datadog.etcd.url=url");
		
		DeploymentPluginService datadogDeploymentPluginService = context.getBean(DeploymentPluginService.class);
		DatadogEtcdClient datadogEtcdClient = this.context.getBean(DatadogEtcdClient.class);
		RestOperations datadogEtcdRestTemplate = this.context.getBean(RestOperations.class);

		assertThat(datadogDeploymentPluginService, not(nullValue()));
		assertThat(datadogEtcdClient, not(nullValue()));
		assertThat(datadogEtcdRestTemplate, not(nullValue()));
		assertThat(datadogEtcdRestTemplate, instanceOf(RestTemplate.class));
		assertThat(((RestTemplate) datadogEtcdRestTemplate).getMessageConverters(), hasSize(2));
	}

	private void load(Class<?> config, String... properties) {
		EnvironmentTestUtils.addEnvironment(environment);
		context = new SpringApplicationBuilder(config)
				.parent(AutoConfigApplication.class)
				.profiles("native")
				.properties(properties)
				.environment(environment).web(false).run();			
	}
}

