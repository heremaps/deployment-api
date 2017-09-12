package com.here.deployment.datadog.etcd.service.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration.Etcd;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.datadog.UnitTestApplication;
import com.here.deployment.datadog.etcd.configproperties.DatadogEtcdClientProperties;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseFailure;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseSuccess;
import com.here.deployment.datadog.etcd.service.impl.DatadogEtcdClientImpl;
import com.here.deployment.domain.PluginResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnitTestApplication.class }, 
	properties = { "plugins.datadog.etcd.url=url" }
)
public class DatadogEtcdClientImplTest {	
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
	
	@SpyBean
	DatadogEtcdClientImpl datadogEtcdClientImpl;		
	
	@MockBean
	CloudConfigClientService cloudConfigClientService;
	
	@MockBean
	EtcdConfiguration etcdConfiguration;
	
	@MockBean
	Etcd etcd;
	
	@MockBean
	DatadogEtcdClientProperties datadogEtcdClientProperties;
	
	static final String FAKE_APP_NAME = "FakeService";
	static final String FAKE_APP_ENV_DEV = "dev";
	static final String FAKE_ENTITY_ID = "entityId";
	static final String FAKE_PAYLOAD = "[{\n  payload: \"payload\"\n}]";
	
	@Before
	public void setUp() {
		doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(datadogEtcdClientImpl).checkNames(any(EtcdConfiguration.class));
		doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(datadogEtcdClientImpl).initConfigs(any(EtcdConfiguration.class));
		doReturn(new ResponseEntity<Void>(HttpStatus.OK)).when(datadogEtcdClientImpl).instances(any(EtcdConfiguration.class));
	}
		
	@Test
	public void test_sendToEtcd_mock() {
		doCallRealMethod().when(datadogEtcdClientImpl).sendToEtcd(any(EtcdConfiguration.class));
		
		PluginResponse response = datadogEtcdClientImpl.sendToEtcd(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseSuccess.class));
		assertThat(((DatadogEtcdPluginResponseSuccess) response).getStatusCode(), equalTo(200));
		
		verify(datadogEtcdClientImpl).checkNames(etcdConfiguration);
		verify(datadogEtcdClientImpl).initConfigs(etcdConfiguration);
		verify(datadogEtcdClientImpl).instances(etcdConfiguration);
	}
	
	@Test
	public void test_httpClientErrorException() {
		doCallRealMethod().when(datadogEtcdClientImpl).sendToEtcd(any(EtcdConfiguration.class));
		doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "error!")).when(datadogEtcdClientImpl).checkNames(any(EtcdConfiguration.class));
		
		PluginResponse response = datadogEtcdClientImpl.sendToEtcd(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));		
	}
	
	@Test
	public void test_checkNames_failed() {
		doCallRealMethod().when(datadogEtcdClientImpl).sendToEtcd(any(EtcdConfiguration.class));
		doReturn(new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)).when(datadogEtcdClientImpl).checkNames(any(EtcdConfiguration.class));
		
		PluginResponse response = datadogEtcdClientImpl.sendToEtcd(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));		
	}
	
	@Test
	public void test_initConfigs_failed() {
		doCallRealMethod().when(datadogEtcdClientImpl).sendToEtcd(any(EtcdConfiguration.class));
		doReturn(new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)).when(datadogEtcdClientImpl).initConfigs(any(EtcdConfiguration.class));
		
		PluginResponse response = datadogEtcdClientImpl.sendToEtcd(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));		
	}
	
	@Test
	public void test_instances_failed() {
		doCallRealMethod().when(datadogEtcdClientImpl).sendToEtcd(any(EtcdConfiguration.class));
		doReturn(new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)).when(datadogEtcdClientImpl).instances(any(EtcdConfiguration.class));
		
		PluginResponse response = datadogEtcdClientImpl.sendToEtcd(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response, instanceOf(DatadogEtcdPluginResponseFailure.class));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getStatusCode(), equalTo(500));
		assertThat(((DatadogEtcdPluginResponseFailure) response).getErrors(), hasSize(1));		
	}

	@Test
	public void test_checkNames_success() {
		int port = wireMockRule.port();
		final String url = "http://localhost:" + port;
		when(datadogEtcdClientProperties.getUrl()).thenReturn(url);
		when(etcdConfiguration.getEtcd()).thenReturn(etcd);
		when(etcd.getEntityId()).thenReturn(FAKE_ENTITY_ID);
		
		stubFor(put(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/check_names")).willReturn(aResponse()
			.withStatus(HttpStatus.OK.value())
			.withHeader("Content-Type", "text/plain")));
		
		doCallRealMethod().when(datadogEtcdClientImpl).checkNames(any(EtcdConfiguration.class));

		ResponseEntity<Void> response = datadogEtcdClientImpl.checkNames(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		verify(putRequestedFor(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/check_names")).withHeader("value",  equalTo("[\"jmx\"]")));
	}
	
	@Test
	public void test_initConfigs_success() {
		int port = wireMockRule.port();
		final String url = "http://localhost:" + port;
		when(datadogEtcdClientProperties.getUrl()).thenReturn(url);
		when(etcdConfiguration.getEtcd()).thenReturn(etcd);
		when(etcd.getEntityId()).thenReturn(FAKE_ENTITY_ID);
		
		stubFor(put(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/init_configs")).willReturn(aResponse()
			.withStatus(HttpStatus.OK.value())
			.withHeader("Content-Type", "text/plain")));
		
		doCallRealMethod().when(datadogEtcdClientImpl).initConfigs(any(EtcdConfiguration.class));

		ResponseEntity<Void> response = datadogEtcdClientImpl.initConfigs(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		verify(putRequestedFor(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/init_configs")).withHeader("value",  equalTo("[{}]")));
	}
	
	@Test
	public void test_instances_success() {
		int port = wireMockRule.port();
		final String url = "http://localhost:" + port;
		when(datadogEtcdClientProperties.getUrl()).thenReturn(url);
		when(etcdConfiguration.getEtcd()).thenReturn(etcd);
		when(etcd.getEntityId()).thenReturn(FAKE_ENTITY_ID);
		when(etcd.getPayload()).thenReturn(FAKE_PAYLOAD);
		
		stubFor(put(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/instances")).willReturn(aResponse()
			.withStatus(HttpStatus.OK.value())
			.withHeader("Content-Type", "text/plain")));
		
		doCallRealMethod().when(datadogEtcdClientImpl).instances(any(EtcdConfiguration.class));

		ResponseEntity<Void> response = datadogEtcdClientImpl.instances(etcdConfiguration);
		assertThat(response, not(nullValue()));
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		verify(putRequestedFor(urlEqualTo("/v2/keys/datadog/check_configs/" + FAKE_ENTITY_ID + "/instances")).withHeader("value",  equalTo("[{payload:\"payload\"}]")));
	}
}

