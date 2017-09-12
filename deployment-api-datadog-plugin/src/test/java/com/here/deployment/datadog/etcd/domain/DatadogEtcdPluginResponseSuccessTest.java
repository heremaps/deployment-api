package com.here.deployment.datadog.etcd.domain;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseSuccess;

@RunWith(JUnit4.class)
public class DatadogEtcdPluginResponseSuccessTest {

	DatadogEtcdPluginResponseSuccess datadogEtcdPluginResponseSuccess;

	@Before
	public void setUp() {		

	}
	
	@Test
	public void test_statusCode_constructor() {
		datadogEtcdPluginResponseSuccess = new DatadogEtcdPluginResponseSuccess(1);
		assertThat(datadogEtcdPluginResponseSuccess, not(nullValue()));
		assertThat(datadogEtcdPluginResponseSuccess.getStatusCode(), equalTo(1));				
	}
	
	@Test
	public void test_statusCode_message_constructor() {
		datadogEtcdPluginResponseSuccess = new DatadogEtcdPluginResponseSuccess(1, "message");
		assertThat(datadogEtcdPluginResponseSuccess, not(nullValue()));
		assertThat(datadogEtcdPluginResponseSuccess.getStatusCode(), equalTo(1));
		assertThat(datadogEtcdPluginResponseSuccess.getMessage(), equalTo("message"));	
	}
}
