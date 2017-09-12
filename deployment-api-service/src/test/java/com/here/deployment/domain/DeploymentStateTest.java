package com.here.deployment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.domain.DeploymentState;

@RunWith(JUnit4.class)
public class DeploymentStateTest {

	@Test
	public void test_valueOf_deploying() {
		assertThat(DeploymentState.valueOf("DEPLOYING"), equalTo(DeploymentState.DEPLOYING));
	}
	
	@Test
	public void test_valueOf_deployed() {
		assertThat(DeploymentState.valueOf("DEPLOYED"), equalTo(DeploymentState.DEPLOYED));
	}
}
