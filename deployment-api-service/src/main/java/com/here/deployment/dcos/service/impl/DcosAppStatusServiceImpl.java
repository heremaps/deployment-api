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
package com.here.deployment.dcos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.dcos.service.DcosAppStatusService;
import com.here.deployment.dcos.service.DcosClientService;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseSuccess;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.exception.DcosException;
import mesosphere.dcos.client.model.v2.GetAppResponse;

/**
 * The Class DcosAppStatusServiceImpl.
 */
@Service
public class DcosAppStatusServiceImpl implements DcosAppStatusService {

	/** The dcos client service. */
	@Autowired
	DcosClientService dcosClientService;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.here.techops.deployment.service.StatusService#appStatus(com.here.techops.deployment.dto.AppStatusRequest)
	 */
	@Override
	public AppStatusResponseSuccess appStatus(AppStatusRequest appStatusRequest, DcosDeploymentConfiguration deploymentConfig) throws DcosException {
		Dcos dcos = dcosClientService.dcosClient(deploymentConfig.getMarathon().getUrl(), appStatusRequest.getUsername(), appStatusRequest.getPassword());
		GetAppResponse getAppResponse = dcos.getApp(deploymentConfig.getMarathon().getAppId());		
		return new AppStatusResponseSuccess(getAppResponse.getApp());
	}
}
