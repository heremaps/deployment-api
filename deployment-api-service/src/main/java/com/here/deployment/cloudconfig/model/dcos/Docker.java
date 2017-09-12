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
 * The Class Docker.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Docker {	
	
	/** The force pull image. */
	protected Boolean forcePullImage;
	
	/** The network. */
	protected String network;
	
	/** The port mappings. */
	protected List<PortMapping> portMappings;
	
	/**
	 * Gets the force pull image.
	 *
	 * @return the force pull image
	 */
	public Boolean getForcePullImage() {
		return forcePullImage;
	}
	
	/**
	 * Sets the force pull image.
	 *
	 * @param forcePullImage the new force pull image
	 */
	public void setForcePullImage(Boolean forcePullImage) {
		this.forcePullImage = forcePullImage;
	}
	
	/**
	 * Gets the network.
	 *
	 * @return the network
	 */
	public String getNetwork() {
		return network;
	}
	
	/**
	 * Sets the network.
	 *
	 * @param network the new network
	 */
	public void setNetwork(String network) {
		this.network = network;
	}
	
	/**
	 * Gets the port mappings.
	 *
	 * @return the port mappings
	 */
	public List<PortMapping> getPortMappings() {
		if (portMappings == null) {
			return null;
		} else {
			return new ArrayList<>(portMappings);
		}
	}

	/**
	 * Sets the port mappings.
	 *
	 * @param portMappings the new port mappings
	 */
	public void setPortMappings(List<PortMapping> portMappings) {
		if (portMappings == null) {
			this.portMappings = new ArrayList<>();
		} else {
			this.portMappings = new ArrayList<>(portMappings);
		}
	}
}
