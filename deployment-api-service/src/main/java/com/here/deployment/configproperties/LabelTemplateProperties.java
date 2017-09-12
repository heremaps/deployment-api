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
package com.here.deployment.configproperties;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class LabelTemplateProperties.
 */
@Configuration
@ConfigurationProperties(prefix = "deployment.label.template")
public class LabelTemplateProperties {
	
	/** The provided. */
	private Map<String, String> provided;
	
	/** The user. */
	private Map<String, String> user;
	
	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String get(String key) {
		if (user == null || user.get(key) == null) {
			return provided.get(key);
		} else {
			return user.get(key);
		}
	}

	/**
	 * Gets the provided.
	 *
	 * @return the provided
	 */
	public Map<String, String> getProvided() {
		return provided;
	}


	/**
	 * Sets the provided.
	 *
	 * @param provided the provided
	 */
	public void setProvided(Map<String, String> provided) {
		this.provided = provided;
	}


	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Map<String, String> getUser() {
		return user;
	}


	/**
	 * Sets the user.
	 *
	 * @param user the user
	 */
	public void setUser(Map<String, String> user) {
		this.user = user;
	}
}
