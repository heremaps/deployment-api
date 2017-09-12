
package com.here.deployment.mapper.impl;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.model.dcos.Constraint;
import com.here.deployment.cloudconfig.model.dcos.Container;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.model.dcos.Docker;
import com.here.deployment.cloudconfig.model.dcos.EnvironmentVariable;
import com.here.deployment.cloudconfig.model.dcos.Fetch;
import com.here.deployment.cloudconfig.model.dcos.HealthCheck;
import com.here.deployment.cloudconfig.model.dcos.Label;
import com.here.deployment.cloudconfig.model.dcos.Marathon;
import com.here.deployment.cloudconfig.model.dcos.Parameter;
import com.here.deployment.cloudconfig.model.dcos.PortMapping;
import com.here.deployment.cloudconfig.model.dcos.Secret;
import com.here.deployment.cloudconfig.model.dcos.UpgradeStrategy;
import com.here.deployment.cloudconfig.model.dcos.Volume;
import com.here.deployment.cloudconfig.model.dcos.VolumeOption;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.DeploymentContext;
import com.here.deployment.mapper.impl.DeploymentConfigToAppMapperImpl;
import mesosphere.dcos.client.model.v2.App;

/**
 * The Class CloudConfigMappingServiceImplTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DeploymentConfigToAppMapperImplTest {

	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(DeploymentConfigToAppMapperImplTest.class);

	@Autowired
	DeploymentConfigToAppMapperImpl deploymentConfigToAppMapperImpl;

	DcosDeploymentConfiguration dcosDeploymentConfiguration;
	
	@Before
	public void setUp() {
	
		dcosDeploymentConfiguration = new DcosDeploymentConfiguration();
		
		Marathon marathon = new Marathon();
		marathon.setAppId("/fakeservice-dev");
		marathon.setCpus(1.0);
		marathon.setMem(512.0);
		marathon.setInstances(1);
		
		Container container = new Container();
		container.setType("DOCKER");
		
		Docker docker = new Docker();
		docker.setForcePullImage(true);
		docker.setNetwork("BRIDGE");
		
		PortMapping portMapping = new PortMapping();
		portMapping.setContainerPort(8080);
		portMapping.setHostPort(0);
		portMapping.setServicePort(9000);
		portMapping.setProtocol("tcp");
		
		Label label = new Label();
		label.setKey("app-label-test");
		label.setValue("app-label-test-value");
		portMapping.setLabels(Arrays.asList(label));		
		docker.setPortMappings(Arrays.asList(portMapping));
		container.setDocker(docker);
		
		Volume volume = new Volume();
		volume.setContainerPath("/vol");
		volume.setHostPath("/vol");
		volume.setMode("RW");
		volume.setId("vol");
		volume.setStatus("status");
		volume.setType("type");
		volume.setName("name");
		volume.setProvider("provider");
	
		VolumeOption volumeOption = new VolumeOption();
		volumeOption.setKey("app-vol-test");
		volumeOption.setValue("app-vol-test-value");
		volume.setOptions(Arrays.asList(volumeOption));
		container.setVolumes(Arrays.asList(volume));		
		marathon.setContainer(container);
		
		UpgradeStrategy upgradeStrategy = new UpgradeStrategy();
		upgradeStrategy.setMinimumHealthCapacity(1.0);
		upgradeStrategy.setMaximumOverCapacity(0.5);
		marathon.setUpgradeStrategy(upgradeStrategy);
		
		Label label1 = new Label();
		label1.setKey("app-label-test");
		label1.setValue("app-label-test-value");
		
		Label label2 = new Label();		
		label2.setKey("HAPROXY_0_BACKEND_HEAD");
		label2.setTemplate("haproxy_backend_head");
		label2.setArgs(Arrays.asList("300s"));	
		
		Label label3 = new Label();		
		label3.setKey("HAPROXY_0_BACKEND_HTTP_HEALTHCHECK_OPTIONS");
		label3.setTemplate("haproxy_backend_health");
		label3.setArgs(Arrays.asList("myhost.com"));	
		
		marathon.setLabels(Arrays.asList(label1, label2, label3));
		
		HealthCheck healthCheck = new HealthCheck();
		healthCheck.setProtocol("MESOS_HTTP");
		healthCheck.setPortIndex(0);
		healthCheck.setGracePeriodSeconds(5);
		healthCheck.setPath("/");
		healthCheck.setIntervalSeconds(10);
		healthCheck.setTimeoutSeconds(10);
		healthCheck.setMaxConsecutiveFailures(3);
		healthCheck.setIgnoreHttp1xx(true);
		healthCheck.setCommand("command");
		marathon.setHealthChecks(Arrays.asList(healthCheck));
		
		Secret secret = new Secret();
		secret.setName("app-secret-test");
		secret.setSource("app-secret-test-value");
		marathon.setSecrets(Arrays.asList(secret));
		
		Fetch fetch = new Fetch();
		fetch.setUri("app-test-fetch");
		fetch.setExecutable(true);
		fetch.setExtract(true);
		fetch.setCache(true);
		fetch.setOutputFile("outputFile");
		marathon.setFetch(Arrays.asList(fetch));
		
		Constraint constraint = new Constraint();
		constraint.setAttribute("app-test-attribute");
		constraint.setOperator("UNIQUE");
		constraint.setValue("app-test-attribute-value");
		marathon.setConstraints(Arrays.asList(constraint));
		
		dcosDeploymentConfiguration.setMarathon(marathon);
		
		DeploymentContext global = new DeploymentContext();
		com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker globalDocker = 
				new com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker();
		Parameter globalDockerParameter = new Parameter("global-docker-param-test", "global-docker-param-test-value");
		globalDocker.setParameters(Arrays.asList(globalDockerParameter));	
		global.setDocker(globalDocker);	
		
		EnvironmentVariable globalEnvironmentVariable = new EnvironmentVariable("global-env-test", "global-env-test-value");
		global.setEnv(Arrays.asList(globalEnvironmentVariable));		
		
		dcosDeploymentConfiguration.setGlobal(global);
		
		DeploymentContext application = new DeploymentContext();
		com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker applicationDocker = 
				new com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker();
		Parameter applicationDockerParameter = new Parameter("application-docker-param-test", "application-docker-param-test-value");
		applicationDocker.setParameters(Arrays.asList(applicationDockerParameter));		
		application.setDocker(applicationDocker);
		
		EnvironmentVariable applicationEnvironmentVariable = new EnvironmentVariable("application-env-test", "application-env-test-value");
		application.setEnv(Arrays.asList(applicationEnvironmentVariable));
		
		dcosDeploymentConfiguration.setApplication(application);
		
		DeploymentContext environment = new DeploymentContext();
		com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker environmentDocker = 
				new com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration.Docker();
		Parameter environmentDockerParameter = new Parameter("environment-docker-param-test", "environment-docker-param-test-value");
		environmentDocker.setParameters(Arrays.asList(environmentDockerParameter));		
		environment.setDocker(environmentDocker);
		
		EnvironmentVariable environmentEnvironmentVariable = new EnvironmentVariable("environment-env-test", "environment-env-test-value");
		environment.setEnv(Arrays.asList(environmentEnvironmentVariable));
		
		dcosDeploymentConfiguration.setEnvironment(environment);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void convert_success()  {
		App app = deploymentConfigToAppMapperImpl.convert(dcosDeploymentConfiguration);
		
		assertThat(app.getId(), equalTo("/fakeservice-dev"));
		assertThat(app.getCpus(), equalTo(1.0));
		assertThat(app.getMem(), equalTo(512.0));
		assertThat(app.getInstances(), equalTo(1));
		assertThat(app.getContainer().getType(), equalTo("DOCKER"));
		assertThat(app.getContainer().getDocker().isForcePullImage(), equalTo(true));
		assertThat(app.getContainer().getDocker().getNetwork(), equalTo("BRIDGE"));
		assertThat(app.getUpgradeStrategy().getMinimumHealthCapacity(), equalTo(1.0));
		assertThat(app.getUpgradeStrategy().getMaximumOverCapacity(), equalTo(0.5));
		
		assertThat(app.getContainer().getDocker().getPortMappings(), 
			hasItem( 
				allOf(
					hasProperty("containerPort", equalTo(8080)),
					hasProperty("hostPort", equalTo(0)),
					hasProperty("servicePort", equalTo(9000)),
					hasProperty("protocol", equalTo("tcp")),
					hasProperty("labels", IsMapContaining.<String, String> hasEntry("app-label-test", "app-label-test-value"))
				)
			)
		);
		
		assertThat(app.getContainer().getVolumes(), 
			hasItem( 
				allOf(
					hasProperty("containerPath", equalTo("/vol")),
					hasProperty("hostPath", equalTo("/vol")),
					hasProperty("mode", equalTo("RW")),
					hasProperty("id", equalTo("vol")),
					hasProperty("status", equalTo("status")),
					hasProperty("type", equalTo("type")),
					hasProperty("name", equalTo("name")),
					hasProperty("provider", equalTo("provider")),
					hasProperty("options", IsMapContaining.<String, String> hasEntry("app-vol-test", "app-vol-test-value"))
				)
			)
		);
		

		assertThat(app.getLabels(), 
			allOf(
				hasEntry("app-label-test", "app-label-test-value"),
				hasEntry("HAPROXY_0_BACKEND_HEAD", "backend {backend}\r\n balance {balance}\r\n mode {mode}\r\n timeout server 300s\r\n timeout client 300s\r\n"),
				hasEntry("HAPROXY_0_BACKEND_HTTP_HEALTHCHECK_OPTIONS", "option httpchk GET {healthCheckPath} HTTP/1.1\\r\\nHost:\\ myhost.com\n")
			)
		);


		assertThat(app.getHealthChecks(), 
			hasItem(
				allOf(
					hasProperty("protocol", equalTo("MESOS_HTTP")),
					hasProperty("portIndex", equalTo(0)),
					hasProperty("gracePeriodSeconds", equalTo(5)),
					hasProperty("path", equalTo("/")),
					hasProperty("intervalSeconds", equalTo(10)),
					hasProperty("timeoutSeconds", equalTo(10)),
					hasProperty("maxConsecutiveFailures", equalTo(3)),
					hasProperty("ignoreHttp1xx", equalTo(true)),
					hasProperty("command", equalTo("command"))
				)
			)
		);
		
		assertThat(app.getSecrets(), hasEntry(equalTo("app-secret-test"), anything()));		
		assertThat(app.getSecrets().get("app-secret-test"), hasProperty("source", equalTo("app-secret-test-value")));
				
		assertThat(app.getConstraints(), contains(IsIterableContainingInOrder.<String>contains("app-test-attribute", "UNIQUE", "app-test-attribute-value")));
		
		assertThat(app.getFetch(), 
			hasItem(
				allOf(
					hasProperty("uri", equalTo("app-test-fetch")),
					hasProperty("executable", equalTo(true)),
					hasProperty("extract", equalTo(true)),
					hasProperty("cache", equalTo(true)),
					hasProperty("outputFile", equalTo("outputFile"))
				)
			)
		);
		
		assertThat(app.getEnv(), hasEntry("global-env-test", "global-env-test-value"));
		assertThat(app.getEnv(), hasEntry("application-env-test", "application-env-test-value"));
		assertThat(app.getEnv(), hasEntry("environment-env-test", "environment-env-test-value"));
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				hasItem(allOf(hasProperty("key", equalTo("global-docker-param-test")), 
				hasProperty("value", equalTo("global-docker-param-test-value")))));
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				hasItem(allOf(hasProperty("key", equalTo("application-docker-param-test")), 
				hasProperty("value", equalTo("application-docker-param-test-value")))));
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				hasItem(allOf(hasProperty("key", equalTo("environment-docker-param-test")), 
				hasProperty("value", equalTo("environment-docker-param-test-value")))));
	}
	
	@Test
	public void test_processEnvironmentVariables_null()  {
		App app = new App();
		
		dcosDeploymentConfiguration.getGlobal().setEnv(null);
		
		deploymentConfigToAppMapperImpl.processEnvironmentVariables(app, dcosDeploymentConfiguration);
		
		assertThat(app.getEnv(), not(hasEntry("global-env-test", "global-env-test-value")));
		assertThat(app.getEnv(), hasEntry("application-env-test", "application-env-test-value"));
		assertThat(app.getEnv(), hasEntry("environment-env-test", "environment-env-test-value"));
	}
	
	@Test
	public void test_processParameters_null()  {
		App app = new App();
		mesosphere.dcos.client.model.v2.Container container = new mesosphere.dcos.client.model.v2.Container();
		mesosphere.dcos.client.model.v2.Docker docker = new mesosphere.dcos.client.model.v2.Docker();
		container.setDocker(docker);
		app.setContainer(container);
		
		dcosDeploymentConfiguration.getGlobal().getDocker().setParameters(null);
		
		deploymentConfigToAppMapperImpl.processDockerParameters(app, dcosDeploymentConfiguration);
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				not(hasItem(allOf(hasProperty("key", equalTo("global-docker-param-test")), 
						hasProperty("value", equalTo("global-docker-param-test-value"))))));
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				hasItem(allOf(hasProperty("key", equalTo("application-docker-param-test")), 
				hasProperty("value", equalTo("application-docker-param-test-value")))));
		
		assertThat(app.getContainer().getDocker().getParameters(), 
				hasItem(allOf(hasProperty("key", equalTo("environment-docker-param-test")), 
				hasProperty("value", equalTo("environment-docker-param-test-value")))));
	}
}
