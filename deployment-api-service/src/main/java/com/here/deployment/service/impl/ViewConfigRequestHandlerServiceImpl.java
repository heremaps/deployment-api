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
package com.here.deployment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.here.deployment.DeploymentApiService;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.service.CloudConfigClientService;
import com.here.deployment.domain.AppConfig;
import com.here.deployment.mapper.DeploymentConfigToAppMapper;
import com.here.deployment.service.ViewConfigRequestHandlerService;
import mesosphere.dcos.client.model.v2.App;

/**
 * The Class ViewConfigRequestHandlerServiceImpl.
 */
@Service
public class ViewConfigRequestHandlerServiceImpl implements ViewConfigRequestHandlerService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DeploymentRequestHandlerServiceImpl.class);
	
	/** The cloud config client service. */
	@Autowired
	CloudConfigClientService cloudConfigClientService;
	
	/** The deployment config to app mapper. */
	@Autowired
	DeploymentConfigToAppMapper deploymentConfigToAppMapper;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.ViewConfigRequestHandlerService#handleViewConfigRequest(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseEntity<String> handleViewConfigRequest(String appName, String appEnv) {	
		try {
			AppConfig<DcosDeploymentConfiguration> appConfig = cloudConfigClientService.fetchConfiguration(
					appName, 
					appEnv, 
					DeploymentApiService.DEFAULT_DEPLOYMENT_PROFILE_NAME, 
					DcosDeploymentConfiguration.class);
			
			App app = deploymentConfigToAppMapper.convert(appConfig.getConfig());
			return new ResponseEntity<String>(app.toString(), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
}
