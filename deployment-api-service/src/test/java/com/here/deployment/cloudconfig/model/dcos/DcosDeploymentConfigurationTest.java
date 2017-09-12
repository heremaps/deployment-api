package com.here.deployment.cloudconfig.model.dcos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.cloudconfig.model.dcos.DcosDeploymentConfiguration;
import com.here.deployment.cloudconfig.model.dcos.EnvironmentVariable;
import com.here.deployment.cloudconfig.model.dcos.Parameter;

@RunWith(SpringRunner.class)
public class DcosDeploymentConfigurationTest {
	
	DcosDeploymentConfiguration dcosDeploymentConfiguration;
	
	ArrayList<EnvironmentVariable> envVars;
	
	EnvironmentVariable env;
	
	ArrayList<Parameter> params;
	
	Parameter param;

	@Before
	public void setUp() {
		dcosDeploymentConfiguration = new DcosDeploymentConfiguration();
		envVars = new ArrayList<>();
		env = new EnvironmentVariable("key", "value");
		envVars.add(env);
		
		params = new ArrayList<>();
		param = new Parameter("key", "value");
		params.add(param);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), nullValue());
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), nullValue());
		assertThat(dcosDeploymentConfiguration.getApplication().getEnv(), nullValue());
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), nullValue());
		assertThat(dcosDeploymentConfiguration.getEnvironment().getEnv(), nullValue());
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {
		dcosDeploymentConfiguration.getGlobal().env = envVars;
		dcosDeploymentConfiguration.getGlobal().getDocker().parameters = params;
		dcosDeploymentConfiguration.getApplication().env = envVars;
		dcosDeploymentConfiguration.getApplication().getDocker().parameters = params;
		dcosDeploymentConfiguration.getEnvironment().env = envVars;
		dcosDeploymentConfiguration.getEnvironment().getDocker().parameters = params;
		
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), not(sameInstance(envVars)));
		
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), not(sameInstance(params)));
		
		assertThat(dcosDeploymentConfiguration.getApplication().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getApplication().getEnv(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getApplication().getEnv(), not(sameInstance(envVars)));
		
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), not(sameInstance(params)));
		
		assertThat(dcosDeploymentConfiguration.getEnvironment().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getEnv(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getEnv(), not(sameInstance(envVars)));
		
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), not(sameInstance(params)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		dcosDeploymentConfiguration.getGlobal().setEnv(null);
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), hasSize(0));
		
		dcosDeploymentConfiguration.getGlobal().getDocker().setParameters(null);
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().getParameters(), hasSize(0));
		
		dcosDeploymentConfiguration.getApplication().setEnv(null);
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), hasSize(0));
		
		dcosDeploymentConfiguration.getApplication().getDocker().setParameters(null);
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().getParameters(), hasSize(0));
		
		dcosDeploymentConfiguration.getEnvironment().setEnv(null);
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getEnv(), hasSize(0));
		
		dcosDeploymentConfiguration.getEnvironment().getDocker().setParameters(null);
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().getParameters(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		dcosDeploymentConfiguration.getGlobal().setEnv(envVars);
		assertThat(dcosDeploymentConfiguration.getGlobal().env, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().env, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getGlobal().env, not(sameInstance(envVars)));
		
		dcosDeploymentConfiguration.getGlobal().getDocker().setParameters(params);
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().parameters, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().parameters, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getGlobal().getDocker().parameters, not(sameInstance(envVars)));
		
		dcosDeploymentConfiguration.getApplication().setEnv(envVars);
		assertThat(dcosDeploymentConfiguration.getApplication().env, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getApplication().env, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getApplication().env, not(sameInstance(envVars)));
		
		dcosDeploymentConfiguration.getApplication().getDocker().setParameters(params);
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().parameters, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().parameters, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getApplication().getDocker().parameters, not(sameInstance(envVars)));
		
		dcosDeploymentConfiguration.getEnvironment().setEnv(envVars);
		assertThat(dcosDeploymentConfiguration.getEnvironment().env, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getEnvironment().env, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getEnvironment().env, not(sameInstance(envVars)));
		
		dcosDeploymentConfiguration.getEnvironment().getDocker().setParameters(params);
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().parameters, not(nullValue()));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().parameters, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(dcosDeploymentConfiguration.getEnvironment().getDocker().parameters, not(sameInstance(envVars)));
	}
}
