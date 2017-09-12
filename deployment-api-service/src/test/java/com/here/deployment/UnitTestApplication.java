package com.here.deployment;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.here.deployment.DeploymentApiService;

/**
 * The Class UnitTestApplication.
 */
@ComponentScan(excludeFilters = { 
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DeploymentApiService.class)
})
@SpringBootApplication
public class UnitTestApplication extends SpringBootServletInitializer {}
	
