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
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseSuccess;
import mesosphere.dcos.client.exception.DcosException;

/**
 * The Interface DcosAppStatusService.
 */
public interface DcosAppStatusService {

	/**
	 * App status.
	 *
	 * @param appStatusRequest the app status request
	 * @param deploymentConfig the deployment config
	 * @return the app status response success
	 * @throws DcosException the dcos exception
	 */
	AppStatusResponseSuccess appStatus(AppStatusRequest appStatusRequest, DcosDeploymentConfiguration deploymentConfig) throws DcosException;
}
