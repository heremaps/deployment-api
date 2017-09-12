package com.here.deployment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.domain.AppConfig;

@RunWith(JUnit4.class)
public class AppConfigTest {

	@Test
	public void test_appConfig() {
		AppConfig<String> appConfig = new AppConfig<String>("test");
		assertThat(appConfig.getConfig(), equalTo("test"));
	}
}
