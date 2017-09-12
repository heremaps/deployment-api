package com.here.deployment.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.domain.PluginResponse;

@RunWith(JUnit4.class)
public class PluginResponseTest {

	@Test
	public void test_appConfig() {
		PluginResponse pluginResponse = new PluginResponse("test") {};
		assertThat(pluginResponse.getPluginName(), equalTo("test"));
		pluginResponse.setPluginName("test2");
		assertThat(pluginResponse.getPluginName(), equalTo("test2"));
	}
}
