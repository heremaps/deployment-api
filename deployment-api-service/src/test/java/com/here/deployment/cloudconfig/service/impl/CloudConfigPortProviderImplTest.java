
package com.here.deployment.cloudconfig.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.service.impl.CloudConfigPortProviderImpl;

/**
 * The Class CloudConfigMappingServiceImplTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnitTestApplication.class }, properties = { "server.port: 8888" })
public class CloudConfigPortProviderImplTest {
	
	@Autowired
	CloudConfigPortProviderImpl cloudConfigPortProviderImpl;

	@Test
	public void getPort_success() {
		assertThat(cloudConfigPortProviderImpl.getPort(), equalTo(8888));
	}
}
