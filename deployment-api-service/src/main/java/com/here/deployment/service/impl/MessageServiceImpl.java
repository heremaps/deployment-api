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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.here.deployment.service.MessageService;

/**
 * The Class MessageServiceImpl.
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	/** The message source. */
	@Autowired
	MessageSource messageSource;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.MessageService#deploymentConflict()
	 */
	public String deploymentConflict() {
		return messageSource.getMessage("deployment.conflict", null, LocaleContextHolder.getLocale());
	}
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.service.MessageService#deploymentBadRequest()
	 */
	public String deploymentBadRequest() {
		return messageSource.getMessage("deployment.badRequest", null, LocaleContextHolder.getLocale());		
	}
}
