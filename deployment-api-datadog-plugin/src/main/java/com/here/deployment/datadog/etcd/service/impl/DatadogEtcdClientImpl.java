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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import com.here.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration;
import com.here.deployment.datadog.etcd.configproperties.DatadogEtcdClientProperties;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseFailure;
import com.here.deployment.datadog.etcd.domain.DatadogEtcdPluginResponseSuccess;
import com.here.deployment.datadog.etcd.service.DatadogEtcdClient;
import com.here.deployment.domain.PluginResponse;

/**
 * The Class DatadogEtcdClientImpl.
 */
public class DatadogEtcdClientImpl implements DatadogEtcdClient {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DatadogEtcdClientImpl.class);
	
	/** The Constant CHECK_CONFIGS_URL. */
	static final String CHECK_CONFIGS_URL = "/v2/keys/datadog/check_configs/";

	static final String MAP_KEY = "value";
	
	/** The datadog etcd client properties. */
	@Autowired
	DatadogEtcdClientProperties datadogEtcdClientProperties;
	
	/** The rest template. */
	@Autowired
	@Qualifier("datadogEtcdRestTemplate")
	RestOperations restTemplate;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.datadog.etcd.service.DatadogEtcdClient#sendToEtcd(com.here.dcos.deployment.cloudconfig.model.datadog.etcd.EtcdConfiguration)
	 */
	@Override
	public PluginResponse sendToEtcd(EtcdConfiguration etcdConfiguration) {
		try {				
			ResponseEntity<Void> checkNamesResponse = checkNames(etcdConfiguration);
			ResponseEntity<Void> initConfigsResponse = initConfigs(etcdConfiguration);
			ResponseEntity<Void> instancesResponse = instances(etcdConfiguration); 
			
			if (checkNamesResponse.getStatusCode().is2xxSuccessful()  
					&& initConfigsResponse.getStatusCode().is2xxSuccessful()  
					&& instancesResponse.getStatusCode().is2xxSuccessful() 
				) {
				return new DatadogEtcdPluginResponseSuccess(HttpStatus.OK.value());
			} else {
				return new DatadogEtcdPluginResponseFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Did not receive successful responses for etcd calls.");
			}
		} catch (HttpClientErrorException httpClientErrorException) {
			LOGGER.error("Could not send to etcd", httpClientErrorException);
			return new DatadogEtcdPluginResponseFailure(httpClientErrorException.getStatusCode().value(), httpClientErrorException.getMessage());
		}
	}
	
	/**
	 * Check names.
	 *
	 * @param etcdConfiguration the etcd configuration
	 * @return the response entity
	 */
	protected ResponseEntity<Void> checkNames(EtcdConfiguration etcdConfiguration) {
		final String checkNamesUrl = new StringBuilder(datadogEtcdClientProperties.getUrl())
				.append(CHECK_CONFIGS_URL)
				.append(etcdConfiguration.getEtcd().getEntityId())
				.append("/check_names").toString();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add(MAP_KEY, "[\"jmx\"]");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map);
		return restTemplate.exchange(checkNamesUrl, HttpMethod.PUT, request, Void.class);
	}
	
	/**
	 * Inits the configs.
	 *
	 * @param etcdConfiguration the etcd configuration
	 * @return the response entity
	 */
	protected ResponseEntity<Void> initConfigs(EtcdConfiguration etcdConfiguration) {
		final String initConfigsUrl = new StringBuilder(datadogEtcdClientProperties.getUrl())
				.append(CHECK_CONFIGS_URL)
				.append(etcdConfiguration.getEtcd().getEntityId())
				.append("/init_configs").toString();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add(MAP_KEY, "[{}]");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map);
		return restTemplate.exchange(initConfigsUrl, HttpMethod.PUT, request, Void.class);
	}
	
	/**
	 * Instances.
	 *
	 * @param etcdConfiguration the etcd configuration
	 * @return the response entity
	 */
	protected ResponseEntity<Void> instances(EtcdConfiguration etcdConfiguration) {
		final String instancesUrl = new StringBuilder(datadogEtcdClientProperties.getUrl())
				.append(CHECK_CONFIGS_URL)
				.append(etcdConfiguration.getEtcd().getEntityId())
				.append("/instances").toString();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add(MAP_KEY, StringUtils.deleteWhitespace(etcdConfiguration.getEtcd().getPayload().replace("\n", "")));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map);
		return restTemplate.exchange(instancesUrl, HttpMethod.PUT, request, Void.class);
	}
}
