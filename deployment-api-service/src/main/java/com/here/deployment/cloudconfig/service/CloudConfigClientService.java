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
package com.here.deployment.cloudconfig.service;

import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.domain.AppConfig;

/**
 * The Interface CloudConfigClientService.
 */
public interface CloudConfigClientService {

	
	/**
	 * Fetches the configuration.
	 *
	 * @param <T> the generic type
	 * @param applicationName the application name
	 * @param environment the environment
	 * @param profileName the profile name
	 * @param appConfigClass the app config class
	 * @return the app config
	 * @throws CloudConfigClientException the cloud config client exception
	 */
	<T> AppConfig<T> fetchConfiguration(String applicationName, String environment, String profileName, Class<T> appConfigClass) throws CloudConfigClientException;
}
