package com.here.deployment.datadog;

import static org.mockito.Mockito.mock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;

/**
 * The Class AutoConfigApplication.
 */
@SpringBootApplication
public class AutoConfigApplication extends SpringBootServletInitializer {

	@Bean
	public CloudConfigClientService cloudConfigClientService() {
		return mock(CloudConfigClientService.class);
	}
}
	
