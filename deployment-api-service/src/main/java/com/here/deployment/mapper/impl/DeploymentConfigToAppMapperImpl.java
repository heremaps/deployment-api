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
package com.here.deployment.mapper.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.DeploymentContext;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker;
import com.here.deployment.mapper.DeploymentConfigToAppMapper;
import com.here.deployment.mapper.LabelTemplateProvider;
import com.here.deployment.mapper.MarathonMapper;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.Parameter;

/**
 * The Class DeploymentConfigToAppMapperImpl.
 */
@Component
public class DeploymentConfigToAppMapperImpl implements DeploymentConfigToAppMapper {
	
	/** The label template provider. */
	@Autowired
	LabelTemplateProvider labelTemplateProvider;
	
	/** The marathon mapper. */
	@Autowired
	MarathonMapper marathonMapper;	
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.DeploymentConfigToAppMapper#convert(com.here.dcos.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration)
	 */
	@Override
	public App convert(DcosDeploymentConfiguration deploymentConfig) {
		App app = marathonMapper.convert(deploymentConfig.getMarathon());	
		
		processEnvironmentVariables(app, deploymentConfig);
		
		processDockerParameters(app, deploymentConfig);
		
		processLabels(app, deploymentConfig);
		
		return app;
	}
	
	/**
	 * Process environment variables.
	 *
	 * @param app the app
	 * @param deploymentConfig the deployment config
	 */
	protected void processEnvironmentVariables(App app, DcosDeploymentConfiguration deploymentConfig) {
		Stream.<DeploymentContext>of(
				deploymentConfig.getGlobal(),
				deploymentConfig.getApplication(), 
				deploymentConfig.getEnvironment()
		).forEach(d -> {
			if (CollectionUtils.isNotEmpty(d.getEnv())) {
				d.getEnv().forEach(e -> {				
					if (StringUtils.isNotEmpty(e.getSecret())) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("secret", e.getSecret());
						app.addEnv(e.getKey(), map);
					} else {
						app.addEnv(e.getKey(), e.getValue());
					}
				});
			}
		});
	}
	
	/**
	 * Process docker parameters.
	 *
	 * @param app the app
	 * @param deploymentConfig the deployment config
	 */
	protected void processDockerParameters(App app, DcosDeploymentConfiguration deploymentConfig) {
		Stream.<Docker>of(
				deploymentConfig.getGlobal().getDocker(), 
				deploymentConfig.getApplication().getDocker(), 
				deploymentConfig.getEnvironment().getDocker()
		).forEach(d -> {
			if (CollectionUtils.isNotEmpty(d.getParameters())) {
				d.getParameters().forEach(p -> {
					app.getContainer().getDocker().addParameter(new Parameter(p.getKey(), p.getValue()));
				});
			}
		});
	}
	
	/**
	 * Process labels.
	 *
	 * @param app the app
	 * @param deploymentConfig the deployment config
	 */
	protected void processLabels(App app, DcosDeploymentConfiguration deploymentConfig) {
		Stream.<DeploymentContext>of(
				deploymentConfig.getGlobal(),
				deploymentConfig.getApplication(), 
				deploymentConfig.getEnvironment()
		).forEach(d -> {
			if (CollectionUtils.isNotEmpty(d.getLabels())) {
				d.getLabels().forEach(a -> {
					if (StringUtils.isNotEmpty(a.getTemplate())) {
						app.addLabel(a.getKey(), labelTemplateProvider.parse(a.getTemplate(), a.getArgs()));
					} else {
						app.addLabel(a.getKey(), a.getValue());
					}
				});
			}
		});
	}
}
