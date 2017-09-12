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

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.dcos.service.DcosAppCreationService;
import com.here.deployment.dcos.service.DcosClientService;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.mapper.DeploymentConfigToAppMapper;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.exception.DcosException;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.Deployment;
import mesosphere.dcos.client.model.v2.UpdateAppResponse;

/**
 * The Class DcosAppCreationServiceImpl.
 */
@Service
public class DcosAppCreationServiceImpl implements DcosAppCreationService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DcosAppCreationServiceImpl.class);
	
	/** The dcos client service. */
	@Autowired
	DcosClientService dcosClientService;
	
	/** The deployment config to app mapper. */
	@Autowired
	DeploymentConfigToAppMapper deploymentConfigToAppMapper;
	
	/**
	 * Creates the app.
	 *
	 * @param deploymentRequest the deployment request
	 * @param deploymentConfig the deployment config
	 * @return the deployment response success
	 * @throws DcosException the dcos exception
	 */
	/* (non-Javadoc)
	 * @see com.here.techops.deployment.dcos.service.DcosDeploymentService#deploy(com.here.techops.deployment.dto.DeploymentRequest)
	 */
	@Override
	public DeploymentResponseSuccess createApp(DeploymentRequest deploymentRequest, DcosDeploymentConfiguration deploymentConfig) throws ConflictException, DcosException {			

		Dcos dcos = dcosClientService.dcosClient(deploymentConfig.getMarathon().getUrl(), deploymentRequest.getUsername(), deploymentRequest.getPassword());
		
		App app = deploymentConfigToAppMapper.convert(deploymentConfig);
		
		app.getContainer().getDocker().setImage(deploymentRequest.getImage());
		
		UpdateAppResponse updateAppResponse = dcos.updateApp(app.getId(), false, app);
		
		boolean foundDeployment = false;
		
		if (StringUtils.isNotEmpty(updateAppResponse.getDeploymentId())) {
			List<Deployment> deployments = dcos.getDeployments();
			if (CollectionUtils.isNotEmpty(deployments)) {
				for (Deployment deployment : deployments) {
					if (updateAppResponse.getDeploymentId().equals(deployment.getId())) {
						foundDeployment = true;
					}
				}
			}			
		}
		
		if (foundDeployment) {
			return new DeploymentResponseSuccess(app.getId());
		} else {
			throw new ConflictException();
		}
	}
	
}
