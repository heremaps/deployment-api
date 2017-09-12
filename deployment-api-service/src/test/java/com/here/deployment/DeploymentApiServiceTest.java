package com.here.deployment;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.DeploymentApiService;

@RunWith(SpringRunner.class)
public class DeploymentApiServiceTest {
	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	private static final String SPRING_STARTUP = "Started DeploymentApiService";
	
	@Test
	public void appContextLoads() throws Exception {
		DeploymentApiService.main(new String[] { "--spring.cloud.config.enabled=false", "--spring.profiles.active=native" });
		assertThat(outputCapture.toString(), containsString(SPRING_STARTUP));
	}
	
}