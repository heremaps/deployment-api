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

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.service.CloudConfigHelperService;
import com.here.deployment.cloudconfig.service.CloudConfigMappingService;
import com.here.deployment.domain.AppConfig;

/**
 * The Class CloudConfigHelperServiceImpl.
 */
@Service
public class CloudConfigHelperServiceImpl implements CloudConfigHelperService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigHelperServiceImpl.class);
	
	/** The Constant COLLECTION_PROPERTY_REGEXP. */
	static final String COLLECTION_PROPERTY_REGEXP = "\\[[0-9]+\\].*";
	
	/** The Constant PROPERTY_OVERRIDE_KEYS. */
	static final String[] PROPERTY_OVERRIDE_KEYS = new String[] {"global.env", "global.docker.parameters", "global.labels", 
			"application.env", "application.docker.parameters", "application.labels"};
	
	static final String GLOBAL_PROFILE = "global";
	
	/** The rest operations. */
	@Autowired
	@Qualifier("cloudConfigRestTemplate")
	RestOperations restOperations;
	
	/** The cloud config mapping service. */
	@Autowired
	CloudConfigMappingService cloudConfigMappingService;

	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.cloudconfig.service.CloudConfigHelperService#fetchEnvironment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Environment fetchEnvironment(String serviceRoot, String applicationName, String environment, String profileName) throws CloudConfigClientException {
		String url = StringUtils.join(serviceRoot, "/", applicationName, "/", GLOBAL_PROFILE, ",", profileName, ",", profileName, "-", environment);

		try {
			return restOperations.exchange(url, HttpMethod.GET, new HttpEntity<>((Void) null, new HttpHeaders()), Environment.class).getBody();			
		} catch (HttpClientErrorException httpClientErrorException) {
			LOGGER.error("Could not fetch environment", httpClientErrorException);
			throw new CloudConfigClientException(httpClientErrorException.getMessage(), httpClientErrorException.getStatusCode().value());
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.cloudconfig.service.CloudConfigHelperService#convertEnvironmentToAppConfig(org.springframework.cloud.config.environment.Environment, java.lang.Class)
	 */
	@Override
	public <T> AppConfig<T> convertEnvironmentToAppConfig(Environment env, Class<T> appConfigClass) throws CloudConfigClientException {
		Properties properties = convertEnvironmentToProperties(env);
		processProperties(properties);
		return cloudConfigMappingService.convertPropertiesToAppConfig(properties, appConfigClass);
	}

	/**
	 * Convert environment to properties.
	 *
	 * @param env the env
	 * @return the properties
	 */
	protected Properties convertEnvironmentToProperties(Environment env) {
		CompositePropertySource composite = new CompositePropertySource("configService");
	
		if (CollectionUtils.isNotEmpty(env.getPropertySources())) {
			env.getPropertySources().forEach(s -> {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) s.getSource();
				composite.addPropertySource(new MapPropertySource(s.getName(), map));
			});
		}

		Properties properties = new Properties();
		for (String key : composite.getPropertyNames()) {
			String value = composite.getProperty(key).toString();			
			properties.setProperty(key, value);
		}
					
		return properties;
	}

	/**
	 * Process properties.
	 *
	 * @param properties the properties
	 */
	protected void processProperties(Properties properties) {
		for (String key : PROPERTY_OVERRIDE_KEYS) {
			if (properties.containsKey(key)) {					
				Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
	
				while (iterator.hasNext()) {
					Map.Entry<Object, Object> entry = iterator.next();		
					Matcher matcher = Pattern.compile(key + COLLECTION_PROPERTY_REGEXP).matcher(entry.getKey().toString());
					if (matcher.matches()) {				
						iterator.remove();
					}
				}
				properties.remove(key);
			}
		}
	}
}
