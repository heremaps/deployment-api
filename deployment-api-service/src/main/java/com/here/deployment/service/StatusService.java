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
package com.here.deployment.service;

import com.here.deployment.domain.AppStatusRequest;
import com.here.deployment.domain.AppStatusResponseSuccess;
import com.here.deployment.exception.BadRequestException;
import com.here.deployment.exception.NotAuthenticatedException;
import com.here.deployment.exception.NotAuthorizedException;
import com.here.deployment.exception.NotFoundException;
import com.here.deployment.exception.ServiceException;

/**
 * The Interface StatusService.
 */
public interface StatusService {
	
	/**
	 * Status.
	 *
	 * @param appStatusRequest the app status request
	 * @return the app status response success
	 * @throws NotFoundException the not found exception
	 * @throws NotAuthenticatedException the not authenticated exception
	 * @throws NotAuthorizedException the not authorized exception
	 * @throws BadRequestException the bad request exception
	 * @throws ServiceException the service exception
	 */
	AppStatusResponseSuccess status(AppStatusRequest appStatusRequest) throws NotFoundException, NotAuthenticatedException, NotAuthorizedException, BadRequestException, ServiceException;
}
