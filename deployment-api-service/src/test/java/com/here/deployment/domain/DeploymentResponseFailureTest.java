package com.here.deployment.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.domain.DeploymentResponseFailure;

@RunWith(SpringRunner.class)
public class DeploymentResponseFailureTest {
	
	@Test
	public void test_default_constructor() {
		DeploymentResponseFailure deploymentResponseFailure = new DeploymentResponseFailure();
		assertThat(deploymentResponseFailure, not(nullValue()));
		assertThat(deploymentResponseFailure.getErrors(), nullValue());
	}

	@Test
	public void test_error_constructor() {
		DeploymentResponseFailure deploymentResponseFailure = new DeploymentResponseFailure("error");
		assertThat(deploymentResponseFailure, not(nullValue()));
		assertThat(deploymentResponseFailure.getErrors(), hasSize(1));
	}
	
	@Test
	public void test_error_list_constructor() {
		DeploymentResponseFailure deploymentResponseFailure = new DeploymentResponseFailure(Arrays.asList("error1", "error2"));
		assertThat(deploymentResponseFailure, not(nullValue()));
		assertThat(deploymentResponseFailure.getErrors(), hasSize(2));
	}
	
	@Test
	public void test_error_constructor_nullList() {
		List<String> nullList = null;
		DeploymentResponseFailure deploymentResponseFailure = new DeploymentResponseFailure(nullList);
		assertThat(deploymentResponseFailure, not(nullValue()));
		assertThat(deploymentResponseFailure.getErrors(), hasSize(0));
	}
	
}
