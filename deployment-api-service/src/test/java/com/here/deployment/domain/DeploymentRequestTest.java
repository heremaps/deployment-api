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
import com.here.deployment.domain.DeploymentRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DeploymentRequestTest {
	
	@Autowired
	Validator validator;

	@Test
	public void test_validate_valid() {
		DeploymentRequest deploymentRequest = new DeploymentRequest();
		deploymentRequest.setUsername("username");
		deploymentRequest.setPassword("password");
		deploymentRequest.setAppName("appName");
		deploymentRequest.setAppEnv("appEnv");
		deploymentRequest.setImage("image");
		Set<ConstraintViolation<DeploymentRequest>> violations = validator.validate(deploymentRequest);
        assertThat(violations, hasSize(0));
        assertThat(deploymentRequest.getUsername(), equalTo("username"));
        assertThat(deploymentRequest.getPassword(), equalTo("password"));
        assertThat(deploymentRequest.getAppName(), equalTo("appName"));
        assertThat(deploymentRequest.getAppEnv(), equalTo("appEnv"));
        assertThat(deploymentRequest.getImage(), equalTo("image"));
        
	}
	
	@Test
	public void test_validate_invalid() {
		DeploymentRequest deploymentRequest = new DeploymentRequest();
		Set<ConstraintViolation<DeploymentRequest>> violations = validator.validate(deploymentRequest);

        assertThat(violations, hasSize(5));
        assertThat(violations, hasItem(hasProperty("message", equalTo("DC/OS username must be provided."))));
        assertThat(violations, hasItem(hasProperty("message", equalTo("DC/OS password must be provided."))));
        assertThat(violations, hasItem(hasProperty("message", equalTo("Application name must be provided."))));        
        assertThat(violations, hasItem(hasProperty("message", equalTo("Application deployment environment must be provided."))));
        assertThat(violations, hasItem(hasProperty("message", equalTo("Docker image path must be provided. For example: <docker_registry>/<docker_repo>/<image_name>:<image_version>"))));

	}
}
