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
 * The Class DcosDeploymentConfiguration.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DcosDeploymentConfiguration {
	
	/** The marathon. */
	protected Marathon marathon;
	
	/** The global. */
	protected DeploymentContext global = new DeploymentContext();
	
	/** The application. */
	protected DeploymentContext application = new DeploymentContext();
	
	/** The environment. */
	protected DeploymentContext environment = new DeploymentContext();
	
	/**
	 * The Class DeploymentContext.
	 */
	@JsonInclude(Include.NON_NULL)
	public static class DeploymentContext {
		
		/** The env. */
		protected List<EnvironmentVariable> env;
		
		/** The labels. */
		protected List<Label> labels;
		
		/** The docker. */
		protected Docker docker = new Docker();
		
		/**
		 * Gets the env.
		 *
		 * @return the env
		 */
		public List<EnvironmentVariable> getEnv() {
			if (env == null) {
				return null;
			} else {
				return new ArrayList<>(env);
			}
		}

		/**
		 * Sets the env.
		 *
		 * @param env the new env
		 */
		public void setEnv(List<EnvironmentVariable> env) {
			if (env == null) {
				this.env = new ArrayList<>();
			} else {
				this.env = new ArrayList<>(env);
			}
		}

		/**
		 * Gets the docker.
		 *
		 * @return the docker
		 */
		public Docker getDocker() {
			return docker;
		}

		/**
		 * Sets the docker.
		 *
		 * @param docker the new docker
		 */
		public void setDocker(Docker docker) {
			this.docker = docker;
		}

		/**
		 * @return the labels
		 */
		public List<Label> getLabels() {
			return labels;
		}

		/**
		 * @param labels the labels to set
		 */
		public void setLabels(List<Label> labels) {
			this.labels = labels;
		}
	}
	
	/**
	 * The Class Docker.
	 */
	@JsonInclude(Include.NON_NULL)
	public static class Docker {
		
		/** The parameters. */
		protected List<Parameter> parameters;
		
		/**
		 * Gets the parameters.
		 *
		 * @return the parameters
		 */
		public List<Parameter> getParameters() {
			if (parameters == null) {
				return null;
			} else {
				return new ArrayList<>(parameters);
			}
		}

		/**
		 * Sets the parameters.
		 *
		 * @param parameters the new parameters
		 */
		public void setParameters(List<Parameter> parameters) {
			if (parameters == null) {
				this.parameters = new ArrayList<>();
			} else {
				this.parameters = new ArrayList<>(parameters);
			}		
		}
	}

	/**
	 * Gets the marathon.
	 *
	 * @return the marathon
	 */
	public Marathon getMarathon() {
		return marathon;
	}

	/**
	 * Sets the marathon.
	 *
	 * @param marathon the new marathon
	 */
	public void setMarathon(Marathon marathon) {
		this.marathon = marathon;
	}

	/**
	 * Gets the global.
	 *
	 * @return the global
	 */
	public DeploymentContext getGlobal() {
		return global;
	}

	/**
	 * Sets the global.
	 *
	 * @param global the new global
	 */
	public void setGlobal(DeploymentContext global) {
		this.global = global;
	}

	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public DeploymentContext getApplication() {
		return application;
	}

	/**
	 * Sets the application.
	 *
	 * @param application the new application
	 */
	public void setApplication(DeploymentContext application) {
		this.application = application;
	}

	/**
	 * Gets the environment.
	 *
	 * @return the environment
	 */
	public DeploymentContext getEnvironment() {
		return environment;
	}

	/**
	 * Sets the environment.
	 *
	 * @param environment the new environment
	 */
	public void setEnvironment(DeploymentContext environment) {
		this.environment = environment;
	}
}