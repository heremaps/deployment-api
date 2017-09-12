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
package mesosphere.dcos.client.model.v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mesosphere.dcos.client.utils.ModelUtils;

/**
 * The Class App.
 */
public class App {
	
	/**
	 * The Class Deployment.
	 */
	public static class Deployment {
		
		/** The id. */
		private String id;

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return ModelUtils.toString(this);
		}
	}

	/** The id. */
	private String id;
	
	/** The cmd. */
	private String cmd;
	
	/** The instances. */
	private Integer instances;
	
	/** The cpus. */
	private Double cpus;
	
	/** The mem. */
	private Double mem;
	
	/** The container. */
	private Container container;
	
	/** The env. */
	private Map<String, Object> env;

	/** The secrets. */
	private Map<String, Secret> secrets;
	
	/** The labels. */
	private Map<String, String> labels;
	
	/** The uris. */
	private Collection<String> uris;
	
	/** The constraints. */
	private List<List<String>> constraints;
	
	/** The fetch. */
	private List<Fetch> fetch;
	
	/** The executor. */
	private String executor;
	
	/** The ports. */
	private List<Integer> ports;
	
	/** The tasks. */
	private Collection<Task> tasks;
	
	/** The tasks staged. */
	private Integer tasksStaged;
	
	/** The tasks running. */
	private Integer tasksRunning;
	
	/** The tasks healthy. */
	private Integer tasksHealthy;
	
	/** The tasks unhealthy. */
	private Integer tasksUnhealthy;
	
	/** The health checks. */
	private List<HealthCheck> healthChecks;
	
	/** The deployments. */
	private List<Deployment> deployments;
	
	/** The last task failure. */
	private TaskFailure lastTaskFailure;
	
	/** The upgrade strategy. */
	private UpgradeStrategy upgradeStrategy;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the cmd.
	 *
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * Sets the cmd.
	 *
	 * @param cmd the new cmd
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
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
	 * Gets the uris.
	 *
	 * @return the uris
	 */
	public Collection<String> getUris() {
		return uris;
	}

	/**
	 * Sets the uris.
	 *
	 * @param uris the new uris
	 */
	public void setUris(Collection<String> uris) {
		this.uris = uris;
	}

	/**
	 * Gets the constraints.
	 *
	 * @return the constraints
	 */
	public List<List<String>> getConstraints() {
		return constraints;
	}

	/**
	 * Sets the constraints.
	 *
	 * @param constraints the new constraints
	 */
	public void setConstraints(List<List<String>> constraints) {
		this.constraints = constraints;
	}

	/**
	 * Adds the constraint.
	 *
	 * @param attribute the attribute
	 * @param operator the operator
	 * @param value the value
	 */
	public void addConstraint(String attribute, String operator, String value) {
		if (this.constraints == null) {
			this.constraints = new ArrayList<List<String>>();
		}
		List<String> constraint = new ArrayList<String>(3);
		constraint.add(attribute == null ? "" : attribute);
		constraint.add(operator == null ? "" : operator);
		constraint.add(value == null ? "" : value);
		this.constraints.add(constraint);
	}

	/**
	 * Gets the fetch.
	 *
	 * @return the fetch
	 */
	public List<Fetch> getFetch() {
		return fetch;
	}

	/**
	 * Sets the fetch.
	 *
	 * @param fetch the new fetch
	 */
	public void setFetch(List<Fetch> fetch) {
		this.fetch = fetch;
	}
	
	/**
	 * Adds the fetch.
	 *
	 * @param fetch the fetch
	 */
	public void addFetch(Fetch fetch) {
		if (this.fetch == null) {
			this.fetch = new ArrayList<>();
		}
		this.fetch.add(fetch);
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
	 * Gets the env.
	 *
	 * @return the env
	 */
	public Map<String, Object> getEnv() {
		return env;
	}

	/**
	 * Sets the env.
	 *
	 * @param env the env
	 */
	public void setEnv(Map<String, Object> env) {
		this.env = env;
	}
	
	/**
	 * Adds the env.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addEnv(String key, Object value) {
		if (this.env == null) {
			this.env = new HashMap<String, Object>();
		}		
		this.env.put(key, value);
	}

	/**
	 * Gets the executor.
	 *
	 * @return the executor
	 */
	public String getExecutor() {
		return executor;
	}

	/**
	 * Sets the executor.
	 *
	 * @param executor the new executor
	 */
	public void setExecutor(String executor) {
		this.executor = executor;
	}

	/**
	 * Gets the ports.
	 *
	 * @return the ports
	 */
	public List<Integer> getPorts() {
		return ports;
	}

	/**
	 * Sets the ports.
	 *
	 * @param ports the new ports
	 */
	public void setPorts(List<Integer> ports) {
		this.ports = ports;
	}

	/**
	 * Adds the uri.
	 *
	 * @param uri the uri
	 */
	public void addUri(String uri) {
		if (this.uris == null) {
			this.uris = new ArrayList<String>();
		}
		this.uris.add(uri);
	}

	/**
	 * Adds the port.
	 *
	 * @param port the port
	 */
	public void addPort(int port) {
		if (this.ports == null) {
			this.ports = new ArrayList<Integer>();
		}
		this.ports.add(port);
	}

	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public Collection<Task> getTasks() {
		return tasks;
	}

	/**
	 * Sets the tasks.
	 *
	 * @param tasks the new tasks
	 */
	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Gets the tasks staged.
	 *
	 * @return the tasks staged
	 */
	public Integer getTasksStaged() {
		return tasksStaged;
	}

	/**
	 * Sets the tasks staged.
	 *
	 * @param tasksStaged the new tasks staged
	 */
	public void setTasksStaged(Integer tasksStaged) {
		this.tasksStaged = tasksStaged;
	}

	/**
	 * Gets the tasks running.
	 *
	 * @return the tasks running
	 */
	public Integer getTasksRunning() {
		return tasksRunning;
	}

	/**
	 * Sets the tasks running.
	 *
	 * @param tasksRunning the new tasks running
	 */
	public void setTasksRunning(Integer tasksRunning) {
		this.tasksRunning = tasksRunning;
	}

	/**
	 * Gets the tasks healthy.
	 *
	 * @return the tasks healthy
	 */
	public Integer getTasksHealthy() {
		return tasksHealthy;
	}

	/**
	 * Sets the tasks healthy.
	 *
	 * @param tasksHealthy the new tasks healthy
	 */
	public void setTasksHealthy(Integer tasksHealthy) {
		this.tasksHealthy = tasksHealthy;
	}

	/**
	 * Gets the tasks unhealthy.
	 *
	 * @return the tasks unhealthy
	 */
	public Integer getTasksUnhealthy() {
		return tasksUnhealthy;
	}

	/**
	 * Sets the tasks unhealthy.
	 *
	 * @param tasksUnhealthy the new tasks unhealthy
	 */
	public void setTasksUnhealthy(Integer tasksUnhealthy) {
		this.tasksUnhealthy = tasksUnhealthy;
	}

	/**
	 * Gets the health checks.
	 *
	 * @return the health checks
	 */
	public List<HealthCheck> getHealthChecks() {
		return healthChecks;
	}

	/**
	 * Sets the health checks.
	 *
	 * @param healthChecks the new health checks
	 */
	public void setHealthChecks(List<HealthCheck> healthChecks) {
		this.healthChecks = healthChecks;
	}
	
	/**
	 * Adds the health check.
	 *
	 * @param healthCheck the health check
	 */
	public void addHealthCheck(HealthCheck healthCheck) {
		if (this.healthChecks == null) {
			this.healthChecks = new ArrayList<>();		
		}
		this.healthChecks.add(healthCheck);
	}

	/**
	 * Gets the deployments.
	 *
	 * @return the deployments
	 */
	public List<Deployment> getDeployments() {
		return deployments;
	}

	/**
	 * Sets the deployments.
	 *
	 * @param deployments the new deployments
	 */
	public void setDeployments(List<Deployment> deployments) {
		this.deployments = deployments;
	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public Map<String, String> getLabels() {
		return labels;
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels the labels
	 */
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	/**
	 * Adds the label.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addLabel(String key, String value) {
		if (this.labels == null) {
			this.labels = new HashMap<String, String>();
		}
		this.labels.put(key, value);
	}

	/**
	 * Gets the last task failure.
	 *
	 * @return the last task failure
	 */
	public TaskFailure getLastTaskFailure() {
		return lastTaskFailure;
	}

	/**
	 * Sets the last task failure.
	 *
	 * @param lastTaskFailure the new last task failure
	 */
	public void setLastTaskFailure(TaskFailure lastTaskFailure) {
		this.lastTaskFailure = lastTaskFailure;
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
	 * Gets the secrets.
	 *
	 * @return the secrets
	 */
	public Map<String, Secret> getSecrets() {
		return secrets;
	}

	/**
	 * Sets the secrets.
	 *
	 * @param secrets the secrets to set
	 */
	public void setSecrets(Map<String, Secret> secrets) {
		this.secrets = secrets;
	}
	
	/**
	 * Adds the secret.
	 *
	 * @param name the name
	 * @param secret the secret
	 */
	public void addSecret(String name, Secret secret) {
		if (this.secrets == null) {
			this.secrets = new HashMap<String, Secret>();
		}		
		this.secrets.put(name, secret);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}

}
