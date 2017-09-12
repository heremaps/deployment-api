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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponse;
import com.here.deployment.domain.AppStatusResponseFailure;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.service.StatusRequestHandlerService;
import com.here.deployment.service.StatusService;

/**
 * The Class StatusRequestHandlerServiceImpl.
 */
@Service
public class StatusRequestHandlerServiceImpl implements StatusRequestHandlerService {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(StatusRequestHandlerServiceImpl.class);
	
	/** The dcos status service. */
	@Autowired
	@Qualifier("dcosStatusService")
	StatusService dcosStatusService;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.StatusRequestHandlerService#handleStatusRequest(com.here.dcos.deployment.domain.AppStatusRequest)
	 */
	@Override
	public ResponseEntity<? extends AppStatusResponse> handleStatusRequest(AppStatusRequest appStatusRequest) {
		try {		
			return new ResponseEntity<AppStatusResponseSuccess>(dcosStatusService.status(appStatusRequest), HttpStatus.OK);					
		} catch (NotFoundException notFoundException) {
			LOGGER.error("appId not found", notFoundException);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
		} catch (NotAuthenticatedException notAuthenticatedException) {
			LOGGER.error("Not authenticated", notAuthenticatedException);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(notAuthenticatedException.getMessage()), HttpStatus.UNAUTHORIZED);		
		} catch (NotAuthorizedException notAuthorizedException) {
			LOGGER.error("Not authorized", notAuthorizedException);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(notAuthorizedException.getMessage()), HttpStatus.FORBIDDEN);
		} catch (BadRequestException badRequestException) {
			LOGGER.error("Bad Request", badRequestException);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);		
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
			return new ResponseEntity<AppStatusResponseFailure>(new AppStatusResponseFailure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
