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
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.App.Deployment;

/**
 * The Class AppStatusResponseSuccess.
 */
public class AppStatusResponseSuccess extends AppStatusResponse {

	/** The app id. */
	protected String appId;
	
	/** The tasks staged. */
	protected int tasksStaged;
	
	/** The tasks running. */
	protected int tasksRunning;
	
	/** The tasks healthy. */
	protected int tasksHealthy;
	
	/** The tasks unhealthy. */
	protected int tasksUnhealthy;
	
	/** The deployments. */
	protected List<Deployment> deployments;
	
	/**
	 * Instantiates a new app status response success.
	 */
	public AppStatusResponseSuccess() {
		super();
	}
	
	/**
	 * Instantiates a new app status response success.
	 *
	 * @param app the app
	 */
	public AppStatusResponseSuccess(App app) {
		this.appId = app.getId();
		this.tasksStaged = app.getTasksStaged();
		this.tasksRunning = app.getTasksRunning();
		this.tasksHealthy = app.getTasksHealthy();
		this.tasksUnhealthy = app.getTasksUnhealthy();		
		this.deployments = app.getDeployments();		
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		if (tasksRunning == tasksHealthy && tasksUnhealthy == 0 && tasksStaged == 0 && (CollectionUtils.isEmpty(deployments))) {
			return DeploymentState.DEPLOYED.getDisplay();
		} else {
			return DeploymentState.DEPLOYING.getDisplay();
		}
	}
	
	/**
	 * Gets the app id.
	 *
	 * @return the app id
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appId the new app id
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * Gets the tasks staged.
	 *
	 * @return the tasks staged
	 */
	public int getTasksStaged() {
		return tasksStaged;
	}

	/**
	 * Sets the tasks staged.
	 *
	 * @param tasksStaged the new tasks staged
	 */
	public void setTasksStaged(int tasksStaged) {
		this.tasksStaged = tasksStaged;
	}

	/**
	 * Gets the tasks running.
	 *
	 * @return the tasks running
	 */
	public int getTasksRunning() {
		return tasksRunning;
	}

	/**
	 * Sets the tasks running.
	 *
	 * @param tasksRunning the new tasks running
	 */
	public void setTasksRunning(int tasksRunning) {
		this.tasksRunning = tasksRunning;
	}

	/**
	 * Gets the tasks healthy.
	 *
	 * @return the tasks healthy
	 */
	public int getTasksHealthy() {
		return tasksHealthy;
	}

	/**
	 * Sets the tasks healthy.
	 *
	 * @param tasksHealthy the new tasks healthy
	 */
	public void setTasksHealthy(int tasksHealthy) {
		this.tasksHealthy = tasksHealthy;
	}

	/**
	 * Gets the tasks unhealthy.
	 *
	 * @return the tasks unhealthy
	 */
	public int getTasksUnhealthy() {
		return tasksUnhealthy;
	}

	/**
	 * Sets the tasks unhealthy.
	 *
	 * @param tasksUnhealthy the new tasks unhealthy
	 */
	public void setTasksUnhealthy(int tasksUnhealthy) {
		this.tasksUnhealthy = tasksUnhealthy;
	}

	/**
	 * Gets the deployments.
	 *
	 * @return the deployments
	 */
	public List<Deployment> getDeployments() {
		if (deployments == null) {
			return null;
		} else {
			return new ArrayList<>(deployments);
		}
	}

	/**
	 * Sets the deployments.
	 *
	 * @param deployments the new deployments
	 */
	public void setDeployments(List<Deployment> deployments) {
		if (deployments == null) {
			this.deployments = new ArrayList<>();
		} else {
			this.deployments = new ArrayList<>(deployments);
		}
	}
}
