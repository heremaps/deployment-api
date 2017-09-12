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
package com.here.deployment.cloudconfig.model.dcos;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class Volume.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Volume {	
	
	/** The container path. */
	protected String containerPath;
	
	/** The host path. */
	protected String hostPath;
	
	/** The mode. */
	protected String mode;
	
	/** The id. */
	protected String id;
	
	/** The status. */
	protected String status;
	
	/** The type. */
	protected String type;
	
	/** The name. */
	protected String name;
	
	/** The provider. */
	protected String provider;
	
	/** The options. */
	protected List<VolumeOption> options;
	
	/**
	 * Gets the container path.
	 *
	 * @return the container path
	 */
	public String getContainerPath() {
		return containerPath;
	}
	
	/**
	 * Sets the container path.
	 *
	 * @param containerPath the new container path
	 */
	public void setContainerPath(String containerPath) {
		this.containerPath = containerPath;
	}
	
	/**
	 * Gets the host path.
	 *
	 * @return the host path
	 */
	public String getHostPath() {
		return hostPath;
	}
	
	/**
	 * Sets the host path.
	 *
	 * @param hostPath the new host path
	 */
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	
	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * Sets the provider.
	 *
	 * @param provider the new provider
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public List<VolumeOption> getOptions() {
		if (options == null) {
			return null;
		} else {
			return new ArrayList<>(options);
		}
	}

	/**
	 * Sets the options.
	 *
	 * @param options the new options
	 */
	public void setOptions(List<VolumeOption> options) {
		if (options == null) {
			this.options = new ArrayList<>();
		} else {
			this.options = new ArrayList<>(options);
		}
	}
}
