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
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.here.deployment.exception.ValidationFailedException;
import com.here.deployment.service.BindingResultHandlerService;

/**
 * The Class BindingResultHandlerServiceImpl.
 */
@Service
public class BindingResultHandlerServiceImpl implements BindingResultHandlerService {

	/* (non-Javadoc)
	 * @see com.here.techops.deployment.service.BindingResultHandlerService#handleBindingResult(org.springframework.validation.BindingResult)
	 */
	@Override
	public void handleBindingResult(BindingResult bindingResult) throws ValidationFailedException {
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
			throw new ValidationFailedException("Validation Failed", errorMessages);
		}
	}
}
