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
 * The Class PortMapping.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortMapping {
	
	/** The container port. */
	protected Integer containerPort;
	
	/** The host port. */
	protected Integer hostPort;
	
	/** The service port. */
	protected Integer servicePort;
	
	/** The protocol. */
	protected String protocol;
	
	/** The labels. */
	protected List<Label> labels;

	/**
	 * Gets the container port.
	 *
	 * @return the container port
	 */
	public Integer getContainerPort() {
		return containerPort;
	}

	/**
	 * Sets the container port.
	 *
	 * @param containerPort the new container port
	 */
	public void setContainerPort(Integer containerPort) {
		this.containerPort = containerPort;
	}

	/**
	 * Gets the host port.
	 *
	 * @return the host port
	 */
	public Integer getHostPort() {
		return hostPort;
	}

	/**
	 * Sets the host port.
	 *
	 * @param hostPort the new host port
	 */
	public void setHostPort(Integer hostPort) {
		this.hostPort = hostPort;
	}

	/**
	 * Gets the service port.
	 *
	 * @return the service port
	 */
	public Integer getServicePort() {
		return servicePort;
	}

	/**
	 * Sets the service port.
	 *
	 * @param servicePort the new service port
	 */
	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}

	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the protocol.
	 *
	 * @param protocol the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public List<Label> getLabels() {
		if (labels == null) {
			return null;
		} else {
			return new ArrayList<>(labels);
		}
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 */
	public void setLabels(List<Label> labels) {
		if (labels == null) {
			this.labels = new ArrayList<>();
		} else {
			this.labels = new ArrayList<>(labels);
		}
	}

}
