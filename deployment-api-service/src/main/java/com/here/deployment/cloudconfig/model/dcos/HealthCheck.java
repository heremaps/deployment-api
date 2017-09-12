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
package com.here.deployment.cloudconfig.model.dcos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class HealthCheck.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthCheck {

	/** The protocol. */
	protected String protocol;
	
	/** The port index. */
	protected Integer portIndex;
	
	/** The grace period seconds. */
	protected Integer gracePeriodSeconds;
	
	/** The path. */
	protected String path;
	
	/** The interval seconds. */
	protected Integer intervalSeconds;
	
	/** The timeout seconds. */
	protected Integer timeoutSeconds;
	
	/** The max consecutive failures. */
	protected Integer maxConsecutiveFailures;
	
	/** The ignore http 1 xx. */
	protected boolean ignoreHttp1xx;
	
	/** The command. */
	protected String command;
	
	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	
	/**
	 * Sets the protocol.
	 *
	 * @param protocol the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * Gets the port index.
	 *
	 * @return the port index
	 */
	public Integer getPortIndex() {
		return portIndex;
	}
	
	/**
	 * Sets the port index.
	 *
	 * @param portIndex the new port index
	 */
	public void setPortIndex(Integer portIndex) {
		this.portIndex = portIndex;
	}
	
	/**
	 * Gets the grace period seconds.
	 *
	 * @return the grace period seconds
	 */
	public Integer getGracePeriodSeconds() {
		return gracePeriodSeconds;
	}
	
	/**
	 * Sets the grace period seconds.
	 *
	 * @param gracePeriodSeconds the new grace period seconds
	 */
	public void setGracePeriodSeconds(Integer gracePeriodSeconds) {
		this.gracePeriodSeconds = gracePeriodSeconds;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the interval seconds.
	 *
	 * @return the interval seconds
	 */
	public Integer getIntervalSeconds() {
		return intervalSeconds;
	}
	
	/**
	 * Sets the interval seconds.
	 *
	 * @param intervalSeconds the new interval seconds
	 */
	public void setIntervalSeconds(Integer intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}
	
	/**
	 * Gets the timeout seconds.
	 *
	 * @return the timeout seconds
	 */
	public Integer getTimeoutSeconds() {
		return timeoutSeconds;
	}
	
	/**
	 * Sets the timeout seconds.
	 *
	 * @param timeoutSeconds the new timeout seconds
	 */
	public void setTimeoutSeconds(Integer timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}
	
	/**
	 * Gets the max consecutive failures.
	 *
	 * @return the max consecutive failures
	 */
	public Integer getMaxConsecutiveFailures() {
		return maxConsecutiveFailures;
	}
	
	/**
	 * Sets the max consecutive failures.
	 *
	 * @param maxConsecutiveFailures the new max consecutive failures
	 */
	public void setMaxConsecutiveFailures(Integer maxConsecutiveFailures) {
		this.maxConsecutiveFailures = maxConsecutiveFailures;
	}

	/**
	 * Checks if is ignore http 1 xx.
	 *
	 * @return true, if is ignore http 1 xx
	 */
	public boolean isIgnoreHttp1xx() {
		return ignoreHttp1xx;
	}

	/**
	 * Sets the ignore http 1 xx.
	 *
	 * @param ignoreHttp1xx the new ignore http 1 xx
	 */
	public void setIgnoreHttp1xx(boolean ignoreHttp1xx) {
		this.ignoreHttp1xx = ignoreHttp1xx;
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets the command.
	 *
	 * @param command the new command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
