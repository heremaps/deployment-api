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
package com.here.deployment.rest;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.here.deployment.config.SwaggerConfig;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponse;
import com.here.deployment.domain.AppStatusResponseFailure;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.domain.DeploymentRequest;
import com.here.deployment.domain.DeploymentResponse;
import com.here.deployment.domain.DeploymentResponseFailure;
import com.here.deployment.domain.DeploymentResponseSuccess;
import com.here.deployment.exception.ValidationFailedException;
import com.here.deployment.service.BindingResultHandlerService;
import com.here.deployment.service.DeploymentRequestHandlerService;
import com.here.deployment.service.MessageService;
import com.here.deployment.service.StatusRequestHandlerService;
import com.here.deployment.service.ViewConfigRequestHandlerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class DcosDeploymentController.
 */
@Controller
@RequestMapping(value = "/v1/dcos")
public class DcosDeploymentController {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DcosDeploymentController.class);
	
	/** The deployment request handler service. */
	@Autowired
	DeploymentRequestHandlerService deploymentRequestHandlerService;

	/** The status request handler service. */
	@Autowired
	StatusRequestHandlerService statusRequestHandlerService;
	
	/** The view config request handler service. */
	@Autowired
	ViewConfigRequestHandlerService viewConfigRequestHandlerService;
	
	/** The binding result handler service. */
	@Autowired
	BindingResultHandlerService bindingResultHandlerService;
	
	/** The message service. */
	@Autowired
	MessageService messageService;

	/**
	 * View config.
	 *
	 * @param appName the app name
	 * @param appEnv the app env
	 * @return the response entity
	 */
	@ApiResponses( value = { 
      @ApiResponse(code = 200, message = "Marathon config returned successfully", response = String.class),	      
      @ApiResponse(code = 404, message = "Marathon config not found", response = String.class),
      @ApiResponse(code = 500, message = "Internal error", response = String.class)
	})
	@ApiOperation(value = "Get marathon.json for Application", tags = { SwaggerConfig.DEPLOYMENT_DCOS_TAG }, position = 0)
	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> viewConfig(@RequestParam("appName") String appName, @RequestParam("appEnv") String appEnv) {		
		return viewConfigRequestHandlerService.handleViewConfigRequest(appName, appEnv);
	}
	
	/**
	 * Deploy.
	 *
	 * @param deploymentRequest the deployment request
	 * @param bindingResult the binding result
	 * @return the response entity
	 */
	@ApiResponses( value = { 
      @ApiResponse(code = 201, message = "Deployment request processed successfully", response = DeploymentResponseSuccess.class),     
      @ApiResponse(code = 400, message = "DC/OS rejected the deployment as invalid", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 401, message = "Username/Password authentication against DC/OS failed", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 403, message = "Username not authorized to perform deployment request for this app in DC/OS", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 404, message = "Deployment confguration for provided appName not found", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 409, message = "DC/OS already has this image version/label deployed to the configured appId", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 422, message = "Validation of deployment request failed", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 500, message = "Internal error", response = DeploymentResponseFailure.class)
	})
	@ApiOperation(value = "Deploy Application to DC/OS", tags = { SwaggerConfig.DEPLOYMENT_DCOS_TAG })
	@RequestMapping(value = "/deploy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<? extends DeploymentResponse> deploy(@Valid @RequestBody DeploymentRequest deploymentRequest, BindingResult bindingResult) {
		try {					
			bindingResultHandlerService.handleBindingResult(bindingResult);					
		} catch (ValidationFailedException validationFailedException) {
			LOGGER.error("Unprocesssable entity error", validationFailedException);
			return new ResponseEntity<DeploymentResponse>(new DeploymentResponseFailure(validationFailedException.getValidationErrors()), HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		return deploymentRequestHandlerService.handleDeploymentRequest(deploymentRequest);
	}
	
	/**
	 * Status.
	 *
	 * @param appStatusRequest the app status request
	 * @param bindingResult the binding result
	 * @return the response entity
	 */
	@ApiResponses( value = { 
      @ApiResponse(code = 200, message = "App status returned successfully", response = AppStatusResponseSuccess.class),	      
      @ApiResponse(code = 400, message = "DC/OS rejected the app status as invalid", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 401, message = "Username/Password authentication against DC/OS failed", response = AppStatusResponseFailure.class),
      @ApiResponse(code = 403, message = "Username not authorized to fetch status for this app in DC/OS", response = AppStatusResponseFailure.class),
      @ApiResponse(code = 404, message = "appId not found in DC/OS", response = AppStatusResponseFailure.class),
      @ApiResponse(code = 422, message = "Validation of app status request failed", response = DeploymentResponseFailure.class),
      @ApiResponse(code = 500, message = "Internal error", response = AppStatusResponseFailure.class)
	})
	@ApiOperation(value = "Get App Status from DC/OS", tags = { SwaggerConfig.DEPLOYMENT_DCOS_TAG })
	@RequestMapping(value = "/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<? extends AppStatusResponse> status(@Valid @RequestBody AppStatusRequest appStatusRequest, BindingResult bindingResult) {
		try {		
			bindingResultHandlerService.handleBindingResult(bindingResult);								
		} catch (ValidationFailedException validationFailedException) {
			LOGGER.error("Unprocesssable entity error", validationFailedException);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(validationFailedException.getValidationErrors()), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return statusRequestHandlerService.handleStatusRequest(appStatusRequest);
	}
}
