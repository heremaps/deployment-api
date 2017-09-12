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
package com.here.deployment.cloudconfig.exception;

/**
 * The Class CloudConfigClientException.
 */
public class CloudConfigClientException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status code. */
	private int statusCode;
	
    /**
     * Instantiates a new cloud config client exception.
     */
    public CloudConfigClientException() {
    	super();
    }

    /**
     * Instantiates a new cloud config client exception.
     *
     * @param message the message
     */
    public CloudConfigClientException(String message) {
		super(message);
	}
    
    /**
     * Instantiates a new cloud config client exception.
     *
     * @param message the message
     * @param statusCode the status code
     */
    public CloudConfigClientException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}
}
