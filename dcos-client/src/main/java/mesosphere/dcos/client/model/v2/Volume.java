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
package mesosphere.dcos.client.model.v2;

import java.util.HashMap;
import java.util.Map;
import mesosphere.dcos.client.utils.ModelUtils;

/**
 * The Class Volume.
 */
public class Volume {
	
	/** The container path. */
	private String containerPath;
	
	/** The host path. */
	private String hostPath;
	
	/** The mode. */
	private String mode;
	
	/** The id. */
	private String id;
	
	/** The status. */
	private String status;
	
	/** The type. */
	private String type;
	
	/** The name. */
	private String name;
	
	/** The provider. */
	private String provider;
	
	/** The options. */
	private Map<String, String> options;

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
	 * @param id the id to set
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
	 * @param status the status to set
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
	 * @param type the type to set
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
	 * @param name the name to set
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
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public Map<String, String> getOptions() {
		return options;
	}

	/**
	 * Sets the options.
	 *
	 * @param options the options to set
	 */
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	/**
	 * Adds the option.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addOption(String key, String value) {
		if (this.options == null) {
			this.options = new HashMap<>();
		}
		options.put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
