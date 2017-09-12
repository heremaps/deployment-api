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

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class Marathon.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Marathon {
	
	/** The url. */
	protected String url;

	/** The app id. */
	protected String appId;

	/** The cpus. */
	protected Double cpus;

	/** The mem. */
	protected Double mem;

	/** The instances. */
	protected Integer instances;

	/** The container. */
	protected Container container;
	
	/** The upgrade strategy. */
	protected UpgradeStrategy upgradeStrategy;

	/** The labels. */
	protected List<Label> labels;

	/** The health checks. */
	protected List<HealthCheck> healthChecks;
	
	/** The secrets. */
	protected List<Secret> secrets;
	
	/** The fetch. */
	protected List<Fetch> fetch;
	
	/** The constraints. */
	protected List<Constraint> constraints;

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * Gets the cpus.
	 *
	 * @return the cpus
	 */
	public Double getCpus() {
		return cpus;
	}

	/**
	 * Sets the cpus.
	 *
	 * @param cpus the new cpus
	 */
	public void setCpus(Double cpus) {
		this.cpus = cpus;
	}

	/**
	 * Gets the mem.
	 *
	 * @return the mem
	 */
	public Double getMem() {
		return mem;
	}

	/**
	 * Sets the mem.
	 *
	 * @param mem the new mem
	 */
	public void setMem(Double mem) {
		this.mem = mem;
	}

	/**
	 * Gets the instances.
	 *
	 * @return the instances
	 */
	public Integer getInstances() {
		return instances;
	}

	/**
	 * Sets the instances.
	 *
	 * @param instances the new instances
	 */
	public void setInstances(Integer instances) {
		this.instances = instances;
	}

	/**
	 * Gets the container.
	 *
	 * @return the container
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Sets the container.
	 *
	 * @param container the new container
	 */
	public void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * Gets the upgrade strategy.
	 *
	 * @return the upgrade strategy
	 */
	public UpgradeStrategy getUpgradeStrategy() {
		return upgradeStrategy;
	}

	/**
	 * Sets the upgrade strategy.
	 *
	 * @param upgradeStrategy the new upgrade strategy
	 */
	public void setUpgradeStrategy(UpgradeStrategy upgradeStrategy) {
		this.upgradeStrategy = upgradeStrategy;
	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public List<Label> getLabels() {
		if (labels == null) {
			return null;
		} else {
			return new ArrayList<>(labels);
		}
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 */
	public void setLabels(List<Label> labels) {
		if (labels == null) {
			this.labels = new ArrayList<>();
		} else {
			this.labels = new ArrayList<>(labels);
		}
	}

	/**
	 * Gets the health checks.
	 *
	 * @return the health checks
	 */
	public List<HealthCheck> getHealthChecks() {
		if (healthChecks == null) {
			return null;
		} else {
			return new ArrayList<>(healthChecks);
		}
	}

	/**
	 * Sets the health checks.
	 *
	 * @param healthChecks the new health checks
	 */
	public void setHealthChecks(List<HealthCheck> healthChecks) {
		if (healthChecks == null) {
			this.healthChecks = new ArrayList<>();
		} else {
			this.healthChecks = new ArrayList<>(healthChecks);
		}
	}

	/**
	 * Gets the secrets.
	 *
	 * @return the secrets
	 */
	public List<Secret> getSecrets() {
		if (secrets == null) {
			return null;
		} else {
			return new ArrayList<>(secrets);
		}
	}

	/**
	 * Sets the secrets.
	 *
	 * @param secrets the new secrets
	 */
	public void setSecrets(List<Secret> secrets) {
		if (secrets == null) {
			this.secrets = new ArrayList<>();
		} else {
			this.secrets = new ArrayList<>(secrets);
		}
	}

	/**
	 * Gets the fetches the.
	 *
	 * @return the fetches the
	 */
	public List<Fetch> getFetch() {
		if (fetch == null) {
			return null;
		} else {
			return new ArrayList<>(fetch);
		}
	}

	/**
	 * Sets the fetches the.
	 *
	 * @param fetch the new fetches the
	 */
	public void setFetch(List<Fetch> fetch) {
		if (fetch == null) {
			this.fetch = new ArrayList<>();
		} else {
			this.fetch = new ArrayList<>(fetch);
		}
	}

	/**
	 * Gets the constraints.
	 *
	 * @return the constraints
	 */
	public List<Constraint> getConstraints() {
		if (constraints == null) {
			return null;
		} else {
			return new ArrayList<>(constraints);
		}
	}

	/**
	 * Sets the constraints.
	 *
	 * @param constraints the new constraints
	 */
	public void setConstraints(List<Constraint> constraints) {
		if (constraints == null) {
			this.constraints = new ArrayList<>();
		} else {
			this.constraints = new ArrayList<>(constraints);
		}
	}
}