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
package com.here.deployment.datadog.etcd.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.here.deployment.datadog.etcd.configproperties.DatadogEtcdClientProperties;
import com.here.deployment.domain.PluginResponse;

/**
 * The Class DatadogEtcdPluginResponseSuccess.
 */
@JsonInclude(Include.NON_EMPTY)
public class DatadogEtcdPluginResponseSuccess extends PluginResponse {
	
	/** The status code. */
	protected	Integer statusCode;
	
	/** The message. */
	protected String message;

	/**
	 * Instantiates a new datadog etcd plugin response success.
	 */
	private DatadogEtcdPluginResponseSuccess() {
		super(DatadogEtcdClientProperties.PLUGIN_NAME);
	}
	
	/**
	 * Instantiates a new datadog etcd plugin response success.
	 *
	 * @param statusCode the status code
	 */
	public DatadogEtcdPluginResponseSuccess(Integer statusCode) {
		this();
		this.statusCode = statusCode;
	}
	
	/**
	 * Instantiates a new datadog etcd plugin response success.
	 *
	 * @param statusCode the status code
	 * @param message the message
	 */
	public DatadogEtcdPluginResponseSuccess(Integer statusCode, String message) {
		this();
		this.statusCode = statusCode;
		this.message = message;
	}
	
	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
