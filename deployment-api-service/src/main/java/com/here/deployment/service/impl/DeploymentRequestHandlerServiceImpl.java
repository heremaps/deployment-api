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

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponse;
import com.here.deployment.domain.DeploymentResponseFailure;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.domain.PluginResponse;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.ConflictException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;
import com.here.deployment.service.DeploymentPluginService;
import com.here.deployment.service.DeploymentRequestHandlerService;
import com.here.deployment.service.DeploymentService;
import com.here.deployment.service.MessageService;

/**
 * The Class DeploymentRequestHandlerServiceImpl.
 */
@Service
public class DeploymentRequestHandlerServiceImpl implements DeploymentRequestHandlerService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DeploymentRequestHandlerServiceImpl.class);
	
	/** The deployment service. */
	@Autowired
	@Qualifier("dcosDeploymentService")
	DeploymentService deploymentService;
	
	/** The deployment plugin services. */
	@Autowired(required = false)
	List<DeploymentPluginService> deploymentPluginServices;
	
	/** The message service. */
	@Autowired
	MessageService messageService;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.DeploymentRequestHandlerService#handleDeploymentRequest(com.here.dcos.deployment.domain.DeploymentRequest)
	 */
	@Override
	public ResponseEntity<? extends DeploymentResponse> handleDeploymentRequest(DeploymentRequest deploymentRequest) {
		try {					
			DeploymentResponseSuccess deploymentResponseSuccess = deploymentService.deploy(deploymentRequest);
			
			if (CollectionUtils.isNotEmpty(deploymentPluginServices)) {
				deploymentPluginServices.forEach(d -> {
					PluginResponse pluginResponse = d.deploy(deploymentRequest);
					if (pluginResponse != null) {
						((DeploymentResponseSuccess) deploymentResponseSuccess).addPluginResponse(pluginResponse);
					}
				});
			}
			
			return new ResponseEntity<DeploymentResponse>(deploymentResponseSuccess, HttpStatus.CREATED);

		} catch (NotFoundException notFoundException) {
			LOGGER.error("Deployment config not found", notFoundException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
		} catch (NotAuthenticatedException notAuthenticatedException) {
			LOGGER.error("Not authenticated", notAuthenticatedException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(notAuthenticatedException.getMessage()), HttpStatus.UNAUTHORIZED);		
		} catch (NotAuthorizedException notAuthorizedException) {
			LOGGER.error("Not authorized", notAuthorizedException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(notAuthorizedException.getMessage()), HttpStatus.FORBIDDEN);		
		} catch (ConflictException conflictException) {
			LOGGER.error("Conflict", conflictException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(messageService.deploymentConflict()), HttpStatus.CONFLICT);				
		} catch (BadRequestException badRequestException) {
			LOGGER.error("Bad Request", badRequestException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(messageService.deploymentBadRequest()), HttpStatus.BAD_REQUEST);	
		} catch (ServiceException serviceException) {
			LOGGER.error("Service exception", serviceException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(serviceException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);			
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
