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
 * The Class DeploymentRequest.
 */
public class DeploymentRequest extends DcosRequest {
	
	/** The image. */
	@NotNull(message = "{image.notempty}")
	@Size(min = 1, max = 5000, message = "{image.notempty}")
	protected String image;
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	@ApiModelProperty(required =  true, value = "Docker image path", position = 5)
	public String getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image) {
		this.image = image;
	}
}
