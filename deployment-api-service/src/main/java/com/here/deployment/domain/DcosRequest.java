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
package com.here.deployment.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DcosRequest.
 */
public abstract class DcosRequest {
	
	/** The username. */
	@NotNull(message = "{username.notempty}")
	@Size(min = 1, max = 1000, message = "{username.notempty}")
	protected String username;
	
	/** The password. */
	@NotNull(message = "{password.notempty}")
	@Size(min = 1, max = 1000, message = "{password.notempty}")
	protected String password;
	
	/** The app name. */
	@NotNull(message = "{appName.notempty}")
	@Size(min = 1, max = 1000, message = "{appName.notempty}")
	protected String appName;
	
	/** The app env. */
	@NotNull(message = "{appEnv.notempty}")
	@Size(min = 1, max = 1000, message = "{appEnv.notempty}")
	protected String appEnv;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@ApiModelProperty(required =  true, value = "DC/OS username", position = 1)
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@ApiModelProperty(required =  true, value = "DC/OS password", position = 2)
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the app name.
	 *
	 * @return the app name
	 */
	@ApiModelProperty(required =  true, value = "Application name", position = 3)
	public String getAppName() {
		return appName;
	}

	/**
	 * Sets the app name.
	 *
	 * @param appName the new app name
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Gets the app env.
	 *
	 * @return the app env
	 */
	@ApiModelProperty(required =  true, value = "Application deployment environment", position = 4)
	public String getAppEnv() {
		return appEnv;
	}

	/**
	 * Sets the app env.
	 *
	 * @param appEnv the new app env
	 */
	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}


}
