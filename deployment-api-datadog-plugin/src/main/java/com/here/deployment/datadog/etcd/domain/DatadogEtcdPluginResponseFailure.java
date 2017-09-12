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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.here.deployment.datadog.etcd.configproperties.DatadogEtcdClientProperties;
import com.here.deployment.domain.PluginResponse;

/**
 * The Class DatadogEtcdPluginResponseFailure.
 */
@JsonInclude(Include.NON_EMPTY)
public class DatadogEtcdPluginResponseFailure extends PluginResponse {

	/** The status code. */
	protected Integer statusCode;
	
	/** The errors. */
	protected List<String> errors;
	
	/**
	 * Instantiates a new datadog etcd plugin response failure.
	 */
	private DatadogEtcdPluginResponseFailure() {
		super(DatadogEtcdClientProperties.PLUGIN_NAME);
	}
	
	/**
	 * Instantiates a new datadog etcd plugin response failure.
	 *
	 * @param statusCode the status code
	 * @param errors the errors
	 */
	public DatadogEtcdPluginResponseFailure(Integer statusCode, List<String> errors) {
		this();
		this.statusCode = statusCode;
		if (errors == null) {
			this.errors = new ArrayList<>();
		} else {
			this.errors = new ArrayList<>(errors);
		}
	}
	
	/**
	 * Instantiates a new datadog etcd plugin response failure.
	 *
	 * @param statusCode the status code
	 * @param error the error
	 */
	public DatadogEtcdPluginResponseFailure(Integer statusCode, String error) {
		this(statusCode, Arrays.asList(error));
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
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		if (errors == null) {
			return null;
		} else {
			return new ArrayList<>(errors);
		}
	}
}
