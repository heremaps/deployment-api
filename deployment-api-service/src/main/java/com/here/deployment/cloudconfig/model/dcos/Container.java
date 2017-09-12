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
 * The Class Container.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Container {
	
	/** The type. */
	protected String type;
	
	/** The docker. */
	protected Docker docker;
	
	/** The volumes. */
	protected List<Volume> volumes;
	
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
	 * Gets the docker.
	 *
	 * @return the docker
	 */
	public Docker getDocker() {
		return docker;
	}
	
	/**
	 * Sets the docker.
	 *
	 * @param docker the new docker
	 */
	public void setDocker(Docker docker) {
		this.docker = docker;
	}

	/**
	 * Gets the volumes.
	 *
	 * @return the volumes
	 */
	public List<Volume> getVolumes() {
		if (volumes == null) {
			return null;
		} else {
			return new ArrayList<>(volumes);
		}
	}

	/**
	 * Sets the volumes.
	 *
	 * @param volumes the new volumes
	 */
	public void setVolumes(List<Volume> volumes) {
		if (volumes == null) {
			this.volumes = new ArrayList<>();
		} else {
			this.volumes = new ArrayList<>(volumes);
		}
	}
}
