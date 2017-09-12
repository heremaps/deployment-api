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
package com.here.deployment.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ValidationFailedException.
 */
public class ValidationFailedException extends DeploymentApiException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The validation errors. */
	protected List<String> validationErrors;
	
	/**
	 * Instantiates a new validation failed exception.
	 */
	public ValidationFailedException() {
		super();
	}

	/**
	 * Instantiates a new validation failed exception.
	 *
	 * @param message the message
	 */
	public ValidationFailedException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new validation failed exception.
	 *
	 * @param message the message
	 * @param validationErrors the validation errors
	 */
	public ValidationFailedException(String message, List<String> validationErrors) {	
		this(message);
		if (validationErrors == null) {
			this.validationErrors = new ArrayList<>();
		} else {
			this.validationErrors = new ArrayList<>(validationErrors);
		}
	}

	/**
	 * Gets the validation errors.
	 *
	 * @return the validation errors
	 */
	public List<String> getValidationErrors() {
		if (validationErrors == null) {
			return null;
		} else {
			return new ArrayList<>(validationErrors);
		}
	}
}
