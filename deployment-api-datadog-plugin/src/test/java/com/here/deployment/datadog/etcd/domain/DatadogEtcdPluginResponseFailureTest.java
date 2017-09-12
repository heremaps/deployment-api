package com.here.deployment.datadog.etcd.domain;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseFailure;

@RunWith(JUnit4.class)
public class DatadogEtcdPluginResponseFailureTest {

	DatadogEtcdPluginResponseFailure datadogEtcdPluginResponseFailure;

	@Before
	public void setUp() {		

	}
	
	@Test
	public void test_statusCode_errors_constructor() {
		datadogEtcdPluginResponseFailure = new DatadogEtcdPluginResponseFailure(1, Arrays.asList("error1", "error2"));
		assertThat(datadogEtcdPluginResponseFailure, not(nullValue()));
		assertThat(datadogEtcdPluginResponseFailure.getStatusCode(), equalTo(1));
		assertThat(datadogEtcdPluginResponseFailure.getErrors(), hasSize(2));		
	}
	
	@Test
	public void test_statusCode_errors_null_constructor() {
		List<String> nullList = null;
		datadogEtcdPluginResponseFailure = new DatadogEtcdPluginResponseFailure(1, nullList);
		assertThat(datadogEtcdPluginResponseFailure, not(nullValue()));
		assertThat(datadogEtcdPluginResponseFailure.getStatusCode(), equalTo(1));
		assertThat(datadogEtcdPluginResponseFailure.getErrors(), not(nullValue()));	
		assertThat(datadogEtcdPluginResponseFailure.getErrors(), hasSize(0));	
	}
	
	@Test
	public void test_statusCode_error_constructor() {
		datadogEtcdPluginResponseFailure = new DatadogEtcdPluginResponseFailure(1, "error1");
		assertThat(datadogEtcdPluginResponseFailure, not(nullValue()));
		assertThat(datadogEtcdPluginResponseFailure.getStatusCode(), equalTo(1));
		assertThat(datadogEtcdPluginResponseFailure.getErrors(), hasSize(1));	
	}
	
	@Test
	public void test_statusCode_error_null_constructor() {
		datadogEtcdPluginResponseFailure = new DatadogEtcdPluginResponseFailure(1, "error1");
		datadogEtcdPluginResponseFailure.errors = null;
		assertThat(datadogEtcdPluginResponseFailure.getErrors(), nullValue());	
	}
	
	
}
