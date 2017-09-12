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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.dcos.service.DcosAppCreationService;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;
import com.here.deployment.service.DeploymentService;
import mesosphere.dcos.client.exception.DcosException;

/**
 * The Class DcosDeploymentServiceImpl.
 */
@Component("dcosDeploymentService")
public class DcosDeploymentServiceImpl implements DeploymentService {

	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DcosDeploymentServiceImpl.class);
	
	/** The cloud config client service. */
	@Autowired
	CloudConfigClientService cloudConfigClientService;
	
	/** The dcos app creation service. */
	@Autowired
	DcosAppCreationService dcosAppCreationService;
	
	/* (non-Javadoc)
	 * @see com.here.techops.deployment.service.DeploymentService#deploy(com.here.techops.deployment.model.DeploymentRequest)
	 */
	@Override
	public DeploymentResponseSuccess deploy(DeploymentRequest deploymentRequest) throws NotFoundException, NotAuthenticatedException, NotAuthorizedException, ConflictException, BadRequestException, ServiceException {
		try {
			AppConfig<DcosDeploymentConfiguration> appConfig = cloudConfigClientService.fetchConfiguration(
					deploymentRequest.getAppName(), 
					deploymentRequest.getAppEnv(), 
					DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME,
					DcosDeploymentConfiguration.class);

			return dcosAppCreationService.createApp(deploymentRequest, appConfig.getConfig());
			
		} catch (CloudConfigClientException cloudConfigClientException) {
			LOGGER.error("Could not fetch from cloud config", cloudConfigClientException);
			if (cloudConfigClientException.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(cloudConfigClientException.getMessage());
			} else {
				throw new ServiceException(cloudConfigClientException.getMessage());
			}
		} catch (DcosException dcosException) {
			LOGGER.error("Could not send to DC/OS", dcosException);
			if (dcosException.getStatus() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(dcosException.getMessage());
			} else if (dcosException.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
				throw new NotAuthenticatedException(dcosException.getMessage());
			} else if (dcosException.getStatus() == HttpStatus.FORBIDDEN.value()) {
				throw new NotAuthorizedException(dcosException.getMessage());
			} else if (dcosException.getStatus() == HttpStatus.CONFLICT.value()) {
				throw new ConflictException(dcosException.getMessage());
			} else if (dcosException.getStatus() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(dcosException.getMessage());				
			} else {
				throw new ServiceException(dcosException.getMessage());
			}
		} catch (NullPointerException nullPointerException) {
			LOGGER.error("Unexepected NPE", nullPointerException);
			throw new BadRequestException();
		}
	}

}
