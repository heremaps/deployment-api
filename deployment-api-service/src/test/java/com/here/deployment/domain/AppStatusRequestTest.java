package com.here.deployment.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.domain.AppStatusRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class AppStatusRequestTest {
	
	@Autowired
	Validator validator;

	@Test
	public void test_validate_valid() {
		AppStatusRequest appStatusRequest = new AppStatusRequest();
		appStatusRequest.setUsername("username");
		appStatusRequest.setPassword("password");
		appStatusRequest.setAppName("appName");
		appStatusRequest.setAppEnv("appEnv");
		Set<ConstraintViolation<AppStatusRequest>> violations = validator.validate(appStatusRequest);
        assertThat(violations, hasSize(0));
        assertThat(appStatusRequest.getUsername(), equalTo("username"));
        assertThat(appStatusRequest.getPassword(), equalTo("password"));
        assertThat(appStatusRequest.getAppName(), equalTo("appName"));
        assertThat(appStatusRequest.getAppEnv(), equalTo("appEnv"));
        
	}
	
	@Test
	public void test_validate_invalid() {
		AppStatusRequest appStatusRequest = new AppStatusRequest();
		Set<ConstraintViolation<AppStatusRequest>> violations = validator.validate(appStatusRequest);

        assertThat(violations, hasSize(4));
        assertThat(violations, hasItem(hasProperty("message", equalTo("DC/OS username must be provided."))));
        assertThat(violations, hasItem(hasProperty("message", equalTo("DC/OS password must be provided."))));
        assertThat(violations, hasItem(hasProperty("message", equalTo("Application name must be provided."))));        
        assertThat(violations, hasItem(hasProperty("message", equalTo("Application deployment environment must be provided."))));
	}
}
