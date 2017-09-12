package com.here.deployment.datadog.etcd.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.cloud.config.server.ConfigServerApplication;
import org.springframework.cloud.config.server.test.ConfigServerTestUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigPortProvider;
import com.here.deployment.cloudconfig.service.impl.CloudConfigClientServiceImpl;
import com.here.deployment.datadog.etcd.service.impl.DatadogEtcdPluginServiceImpl;
import com.here.deployment.domain.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ConfigServerApplication.class, DeploymentApiService.class },
	webEnvironment = WebEnvironment.RANDOM_PORT,
	properties = {
		"plugins.datadog.etcd.url=url",
		"spring.cloud.config.server.prefix: /config",
		"spring.cloud.config.server.native.search-locations: file:./target/repos/config-repo/, file:./target/repos/config-repo/{application}"		
	}
)
@ActiveProfiles("native")
public class DatadogEtcdClientImplIntegrationTest {	
	
	static final Logger LOGGER = LoggerFactory.getLogger(DatadogEtcdClientImplIntegrationTest.class);
	
	@LocalServerPort
	private int port;
	
	@Autowired
	DatadogEtcdPluginServiceImpl datadogEtcdPluginServiceImpl;
	
	@Autowired
	CloudConfigClientServiceImpl cloudConfigClientService;
	
	@MockBean
	CloudConfigPortProvider cloudConfigPortProvider;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	String payloadFile;
	
	static final String FAKE_APP_NAME = "FakeService";
	static final String FAKE_APP_ENV_DEV = "dev";
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigServerTestUtils.prepareLocalRepo();
	}
	
	@Before
	public void setUp() throws IOException {
		when(cloudConfigPortProvider.getPort()).thenReturn(port);
		
		Resource payloadFileResource = resourceLoader.getResource("classpath:fake_service_etcd_dev_test.json");
		payloadFile = Files.lines(payloadFileResource.getFile().toPath()).collect(Collectors.joining());
	}

	@Test
	public void fetchConfiguration_integration_dev_success() throws JSONException {
		AppConfig<EtcdConfiguration> config = cloudConfigClientService.fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, 
				datadogEtcdPluginServiceImpl.pluginName(), EtcdConfiguration.class);
				
		assertThat(config.getConfig().getEtcd().getEntityId(), equalTo("fakeservice-dev"));
        JSONAssert.assertEquals(payloadFile, config.getConfig().getEtcd().getPayload(), false);
	}
}

