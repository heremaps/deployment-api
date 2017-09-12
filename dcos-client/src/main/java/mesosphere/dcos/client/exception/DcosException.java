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
package mesosphere.dcos.client.exception;

/**
 * The Class DcosException.
 */
public class DcosException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	private int status;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new dcos exception.
	 *
	 * @param status the status
	 * @param message the message
	 */
	public DcosException(int status, String message) {
		this.status = status;
		this.message = message;
	}

    /**
     * Gets the HTTP status code of the failure, such as 404.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
	public String getMessage() {
		return message + " (http status: " + status + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return message + " (http status: " + status + ")";
	}
}
