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
package com.here.deployment.datadog.etcd.configproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * The Class DatadogEtcdClientProperties.
 */
@Configuration
@ConfigurationProperties(DatadogEtcdClientProperties.PREFIX)
public class DatadogEtcdClientProperties {

	/** The Constant PLUGIN_NAME. */
	public static final String PLUGIN_NAME = "datadog-etcd";
	
	/** The Constant PREFIX. */
	public static final String PREFIX = "plugins.datadog.etcd";
	
	/** The url. */
	private String url;

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
