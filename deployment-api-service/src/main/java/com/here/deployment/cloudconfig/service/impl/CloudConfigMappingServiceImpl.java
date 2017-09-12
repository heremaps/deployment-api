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

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.service.CloudConfigMappingService;
import com.here.deployment.domain.AppConfig;

/**
 * The Class CloudConfigMappingServiceImpl.
 */
@Service
public class CloudConfigMappingServiceImpl implements CloudConfigMappingService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigMappingServiceImpl.class);
	
	/** The java props mapper. */
	JavaPropsMapper javaPropsMapper;
	
	/**
	 * Instantiates a new cloud config mapping service impl.
	 */
	public CloudConfigMappingServiceImpl() {
		this.javaPropsMapper = new JavaPropsMapper();
	}

	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.cloudconfig.service.CloudConfigMappingService#convertPropertiesToAppConfig(java.util.Properties, java.lang.Class)
	 */
	@Override
	public <T> AppConfig<T> convertPropertiesToAppConfig(Properties properties, Class<T> appConfigClass) throws CloudConfigClientException {
		try {
			T appConfig = javaPropsMapper.readValue(properties, appConfigClass);
			return new AppConfig<T>(appConfig);
		} catch (IOException ioException) {
			LOGGER.error("Unexpected error transforming yml to properties", ioException);
			throw new CloudConfigClientException(ioException.getMessage());
		}
	}
	
	/**
	 * Sets the java props mapper.
	 *
	 * @param javaPropsMapper the new java props mapper
	 */
	protected void setJavaPropsMapper(JavaPropsMapper javaPropsMapper) {
		this.javaPropsMapper = javaPropsMapper;
	}
}
