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
package com.here.deployment.dcos.service;

import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.ConflictException;
import mesosphere.dcos.client.exception.DcosException;

/**
 * The Interface DcosAppCreationService.
 */
public interface DcosAppCreationService {
	
	/**
	 * Creates the app.
	 *
	 * @param deploymentRequest the deployment request
	 * @param deploymentConfig the deployment config
	 * @return the deployment response success
	 * @throws ConflictException the conflict exception
	 * @throws DcosException the dcos exception
	 */
	DeploymentResponseSuccess createApp(DeploymentRequest deploymentRequest, DcosDeploymentConfiguration deploymentConfig) throws ConflictException, DcosException;
}
