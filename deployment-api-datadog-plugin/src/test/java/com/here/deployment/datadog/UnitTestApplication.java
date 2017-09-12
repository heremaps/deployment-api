package com.here.deployment.datadog;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import com.here.deployment.datadog.etcd.config.DatadogClientAutoConfiguration;

/**
 * The Class UnitTestApplication.
 */
@SpringBootApplication
@Import({ DatadogClientAutoConfiguration.class})
public class UnitTestApplication extends SpringBootServletInitializer {}
	
