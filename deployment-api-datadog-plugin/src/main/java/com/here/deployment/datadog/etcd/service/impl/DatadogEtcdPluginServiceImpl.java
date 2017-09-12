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
package com.here.deployment.datadog.etcd.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseFailure;
import com.here.deployment.datadog.etcd.service.DatadogEtcdClient;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.PluginResponse;
import com.here.deployment.service.DeploymentPluginService;

/**
 * The Class DatadogEtcdPluginServiceImpl.
 */
public class DatadogEtcdPluginServiceImpl implements DeploymentPluginService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DatadogEtcdPluginServiceImpl.class);
	
	/** The Constant PLUGIN_NAME. */
	static final String PLUGIN_NAME = "etcd";
	
	/** The cloud config client service. */
	@Autowired
	CloudConfigClientService cloudConfigClientService;
	
	/** The datadog etcd client. */
	@Autowired
	DatadogEtcdClient datadogEtcdClient;

	/**
	 * Plugin name.
	 *
	 * @return the string
	 */
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.DeploymentPluginService#pluginName()
	 */
	@Override
	public String pluginName() {
		return PLUGIN_NAME;
	}
	
	/**
	 * Deploy.
	 *
	 * @param deploymentRequest the deployment request
	 * @return the plugin response
	 */
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.DeploymentPluginService#deploy(com.here.dcos.deployment.domain.DeploymentRequest)
	 */
	@Override
	public PluginResponse deploy(DeploymentRequest deploymentRequest) {
		try {
			AppConfig<EtcdConfiguration> appConfig = cloudConfigClientService.fetchConfiguration(
					deploymentRequest.getAppName(), 
					deploymentRequest.getAppEnv(), 
					PLUGIN_NAME,
					EtcdConfiguration.class);
			
			if (appConfig.getConfig() != null 
					&& appConfig.getConfig().getEtcd() != null
					&& StringUtils.isNotEmpty(appConfig.getConfig().getEtcd().getEntityId()) 
					&& StringUtils.isNotEmpty(appConfig.getConfig().getEtcd().getPayload())
				) {
				return datadogEtcdClient.sendToEtcd(appConfig.getConfig());
			} else {
				return null; // didn't send anything, no response
			}
		} catch (CloudConfigClientException cloudConfigClientException) {
			LOGGER.error("Could not fetch from cloud config", cloudConfigClientException);
			return new DatadogEtcdPluginResponseFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), cloudConfigClientException.getMessage());
		} catch (Exception e) {
			LOGGER.error("Could not send to etcd", e);
			return new DatadogEtcdPluginResponseFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
}
