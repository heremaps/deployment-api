package com.here.deployment.datadog.etcd.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration.Etcd;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.datadog.UnitTestApplication;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseFailure;
import com.here.deployment.datadog.etcd.service.DatadogEtcdClient;
import com.here.deployment.datadog.etcd.service.impl.DatadogEtcdPluginServiceImpl;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.PluginResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnitTestApplication.class }, 
	properties = { "plugins.datadog.etcd.url=url" }
)
public class DatadogEtcdPluginServiceImplTest {	
	
	@Autowired
	DatadogEtcdPluginServiceImpl datadogEtcdPluginServiceImpl;		
	
	@MockBean
	CloudConfigClientService cloudConfigClientService;
	
	@MockBean
	DatadogEtcdClient datadogEtcdClient;
	
	@MockBean
	AppConfig<EtcdConfiguration> appConfig;
	
	@MockBean
	EtcdConfiguration etcdConfiguration;
	
	@MockBean
	Etcd etcd;
	
	@MockBean
	PluginResponse pluginResponse;
	
	@MockBean
	DeploymentRequest deploymentRequest;
	
	static final String FAKE_APP_NAME = "FakeService";
	static final String FAKE_APP_ENV_DEV = "dev";
	static final String FAKE_ENTITY_ID = "entityId";
	static final String FAKE_PAYLOAD = "{ payload: \"payload\" }";
	
	@Before
	public void setUp() {
		when(deploymentRequest.getAppName()).thenReturn(FAKE_APP_NAME);
		when(deploymentRequest.getAppEnv()).thenReturn(FAKE_APP_ENV_DEV);
		when(cloudConfigClientService.fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<EtcdConfiguration>>any()))
			.thenReturn(appConfig);
		
		when(appConfig.getConfig()).thenReturn(etcdConfiguration);
		when(etcdConfiguration.getEtcd()).thenReturn(etcd);
		when(etcd.getEntityId()).thenReturn(FAKE_ENTITY_ID);
		when(etcd.getPayload()).thenReturn(FAKE_PAYLOAD);
		when(datadogEtcdClient.sendToEtcd(any(EtcdConfiguration.class))).thenReturn(pluginResponse);
	}
	
	@Test
	public void test_pluginName() {
		assertThat(datadogEtcdPluginServiceImpl.pluginName(), equalTo(DatadogEtcdPluginServiceImpl.PLUGIN_NAME));
	}
	
	@Test
	public void test_deploy_successful() {
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, not(nullValue()));
		
		verify(cloudConfigClientService).fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, DatadogEtcdPluginServiceImpl.PLUGIN_NAME, EtcdConfiguration.class);
		verify(deploymentRequest).getAppName();
		verify(deploymentRequest).getAppEnv();
		verify(datadogEtcdClient).sendToEtcd(etcdConfiguration);		
	}
	
	@Test
	public void test_deploy_cloudConfigClientException() {
		doThrow(new CloudConfigClientException("error!")).when(cloudConfigClientService).fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<EtcdConfiguration>>any());
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));
	}
	
	@Test
	public void test_deploy_otherException() {
		doThrow(new RuntimeException("error!")).when(cloudConfigClientService).fetchConfiguration(anyString(), anyString(), anyString(), Matchers.<Class<EtcdConfiguration>>any());
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));
	}
	
	@Test
	public void test_deploy_nullop1() {
		when(appConfig.getConfig()).thenReturn(null);
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, nullValue());
		
		verify(cloudConfigClientService).fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, DatadogEtcdPluginServiceImpl.PLUGIN_NAME, EtcdConfiguration.class);		
	}
	
	@Test
	public void test_deploy_nullop2() {
		when(etcdConfiguration.getEtcd()).thenReturn(null);
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, nullValue());
		
		verify(cloudConfigClientService).fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, DatadogEtcdPluginServiceImpl.PLUGIN_NAME, EtcdConfiguration.class);		
	}
	
	@Test
	public void test_deploy_nullop3() {
		when(etcd.getEntityId()).thenReturn(null);
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, nullValue());
		
		verify(cloudConfigClientService).fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, DatadogEtcdPluginServiceImpl.PLUGIN_NAME, EtcdConfiguration.class);		
	}
	
	@Test
	public void test_deploy_nullop4() {
		when(etcd.getPayload()).thenReturn(null);
		PluginResponse response = datadogEtcdPluginServiceImpl.deploy(deploymentRequest);
		assertThat(response, nullValue());
		
		verify(cloudConfigClientService).fetchConfiguration(FAKE_APP_NAME, FAKE_APP_ENV_DEV, DatadogEtcdPluginServiceImpl.PLUGIN_NAME, EtcdConfiguration.class);		
	}
	
	
	
}

