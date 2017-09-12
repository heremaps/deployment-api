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
package com.here.deployment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class AppStatusResponseFailure.
 */
public class AppStatusResponseFailure extends AppStatusResponse {

	/** The errors. */
	protected List<String> errors;

	/**
	 * Instantiates a new app status response failure.
	 */
	public AppStatusResponseFailure() {
		super();
	}

	/**
	 * Instantiates a new app status response failure.
	 *
	 * @param errors the errors
	 */
	public AppStatusResponseFailure(List<String> errors) {
		if (errors == null) {
			this.errors = new ArrayList<>();
		} else {
			this.errors = new ArrayList<>(errors);
		}
	}
	
	/**
	 * Instantiates a new app status response failure.
	 *
	 * @param error the error
	 */
	public AppStatusResponseFailure(String error) {		
		this(Arrays.asList(error));
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		if (errors == null) {
			return null;
		} else {
			return new ArrayList<>(errors);
		}
	}
}
