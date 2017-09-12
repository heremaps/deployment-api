package com.here.deployment.exception;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.exception.DeploymentApiException;
import com.here.deployment.exception.NotAuthorizedException;

@RunWith(JUnit4.class)
public class NotAuthorizedExceptionTest {

	@Test
	public void test_default_constructor() {
		NotAuthorizedException exception = new NotAuthorizedException();
		assertThat(exception, not(nullValue()));
		assertThat(exception, instanceOf(DeploymentApiException.class));
	}
	
	@Test
	public void test_string_constructor() {
		NotAuthorizedException exception = new NotAuthorizedException("error!");
		assertThat(exception, not(nullValue()));
		assertThat(exception, instanceOf(DeploymentApiException.class));
		assertThat(exception.getMessage(), equalTo("error!"));
	}
}
