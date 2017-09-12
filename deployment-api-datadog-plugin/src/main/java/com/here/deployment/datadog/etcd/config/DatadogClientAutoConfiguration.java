/*
* Copyright (c) 2017 HERE Europe B.V.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.here.deployment.datadog.etcd.config;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import com.here.deployment.datadog.etcd.configproperties.DatadogEtcdClientProperties;
import com.here.deployment.datadog.etcd.service.DatadogEtcdClient;
import com.here.deployment.datadog.etcd.service.impl.DatadogEtcdClientImpl;
import com.here.deployment.datadog.etcd.service.impl.DatadogEtcdPluginServiceImpl;
import com.here.deployment.service.DeploymentPluginService;

/**
 * The Class DatadogClientAutoConfiguration.
 */
@Configuration
@ConditionalOnProperty(prefix = DatadogEtcdClientProperties.PREFIX, name = "url")
public class DatadogClientAutoConfiguration {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DatadogClientAutoConfiguration.class);
	
	/**
	 * Datadog deployment plugin service.
	 *
	 * @return the deployment plugin service
	 */
	@Bean
	public DeploymentPluginService datadogDeploymentPluginService() {
		return new DatadogEtcdPluginServiceImpl();
	}
	
	/**
	 * Datadog etcd client.
	 *
	 * @return the datadog etcd client
	 */
	@Bean
	public DatadogEtcdClient datadogEtcdClient() {
		return new DatadogEtcdClientImpl();
	}
	
	/**
	 * Datadog etcd rest template.
	 *
	 * @return the rest operations
	 */
	@Bean
	public RestOperations datadogEtcdRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(new FormHttpMessageConverter(), new StringHttpMessageConverter()));
		return restTemplate;
	}	

}
