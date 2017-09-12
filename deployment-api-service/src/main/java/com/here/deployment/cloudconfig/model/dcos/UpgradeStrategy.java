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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class UpgradeStrategy.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradeStrategy {
	
	/** The minimum health capacity. */
	protected Double minimumHealthCapacity;
	
	/** The maximum over capacity. */
	protected Double maximumOverCapacity;

	/**
	 * Gets the minimum health capacity.
	 *
	 * @return the minimum health capacity
	 */
	public Double getMinimumHealthCapacity() {
		return minimumHealthCapacity;
	}

	/**
	 * Sets the minimum health capacity.
	 *
	 * @param minimumHealthCapacity the new minimum health capacity
	 */
	public void setMinimumHealthCapacity(Double minimumHealthCapacity) {
		this.minimumHealthCapacity = minimumHealthCapacity;
	}

	/**
	 * Gets the maximum over capacity.
	 *
	 * @return the maximum over capacity
	 */
	public Double getMaximumOverCapacity() {
		return maximumOverCapacity;
	}

	/**
	 * Sets the maximum over capacity.
	 *
	 * @param maximumOverCapacity the new maximum over capacity
	 */
	public void setMaximumOverCapacity(Double maximumOverCapacity) {
		this.maximumOverCapacity = maximumOverCapacity;
	}

}
