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
package com.here.deployment.cloudconfig.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.stereotype.Service;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.cloudconfig.service.CloudConfigHelperService;
import com.here.deployment.cloudconfig.service.CloudConfigPortProvider;
import com.here.deployment.domain.AppConfig;

/**
 * The Class CloudConfigClientServiceImpl.
 */
@Service
public class CloudConfigClientServiceImpl implements CloudConfigClientService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigClientServiceImpl.class);
	
	/** The Constant CLOUD_CONFIG_SERVER_URL. */
	static final String CLOUD_CONFIG_SERVER_URL = "http://localhost";
	
	/** The Constant CLOUD_CONFIG_CONTEXT_ROOT. */
	static final String CLOUD_CONFIG_CONTEXT_ROOT = "/config";	
	
	/** The cloud config helper service. */
	@Autowired
	CloudConfigHelperService cloudConfigHelperService;
	
	/** The cloud config port provider. */
	@Autowired
	CloudConfigPortProvider cloudConfigPortProvider;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.cloudconfig.service.CloudConfigClientService#fetchConfiguration(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> AppConfig<T> fetchConfiguration(String applicationName, String environment, String profileName, Class<T> appConfigClass) throws CloudConfigClientException {						
		Environment env = cloudConfigHelperService.fetchEnvironment(
				StringUtils.join(CLOUD_CONFIG_SERVER_URL, ":", cloudConfigPortProvider.getPort(), CLOUD_CONFIG_CONTEXT_ROOT), applicationName, environment, profileName);		
		return cloudConfigHelperService.convertEnvironmentToAppConfig(env, appConfigClass);	
	}

}
