package com.here.deployment.cloudconfig.exception;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;

@RunWith(JUnit4.class)
public class CloudConfigClientExceptionTest {

	@Test
	public void test_default_constructor() {
		CloudConfigClientException cloudConfigClientException = new CloudConfigClientException();
		assertThat(cloudConfigClientException, not(nullValue()));
		assertThat(cloudConfigClientException, instanceOf(RuntimeException.class));
	}
	
	@Test
	public void test_string_constructor() {
		CloudConfigClientException cloudConfigClientException = new CloudConfigClientException("error!");
		assertThat(cloudConfigClientException, not(nullValue()));
		assertThat(cloudConfigClientException, instanceOf(RuntimeException.class));
		assertThat(cloudConfigClientException.getMessage(), equalTo("error!"));
	}
	
	@Test
	public void test_string_int_constructor() {
		CloudConfigClientException cloudConfigClientException = new CloudConfigClientException("error!", 99);
		assertThat(cloudConfigClientException, not(nullValue()));
		assertThat(cloudConfigClientException, instanceOf(RuntimeException.class));
		assertThat(cloudConfigClientException.getMessage(), equalTo("error!"));
		assertThat(cloudConfigClientException.getStatusCode(), equalTo(99));
	}
}
