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
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.cloudconfig.model.dcos.Constraint;
import com.here.deployment.cloudconfig.model.dcos.Fetch;
import com.here.deployment.cloudconfig.model.dcos.HealthCheck;
import com.here.deployment.cloudconfig.model.dcos.Label;
import com.here.deployment.cloudconfig.model.dcos.Marathon;
import com.here.deployment.cloudconfig.model.dcos.Secret;

@RunWith(SpringRunner.class)
public class MarathonTest {

	Marathon marathon;
	
	List<Label> labels;
	
	Label label;
	
	List<HealthCheck> healthChecks;
		
	HealthCheck healthCheck;
		
	List<Secret> secrets;
		
	Secret secret;
		
	List<Fetch> fetches;
		
	Fetch fetch;
		
	List<Constraint> constraints;
	
	Constraint constraint;
	
	@Before
	public void setUp() {
		marathon = new Marathon();
		labels = new ArrayList<>();
		label = new Label();
		label.setKey("key");	
		labels.add(label);
		
		healthChecks = new ArrayList<>();
		healthCheck = new HealthCheck();
		healthCheck.setPortIndex(1);	
		healthChecks.add(healthCheck);
		
		secrets = new ArrayList<>();
		secret = new Secret();
		secret.setName("name");	
		secrets.add(secret);
		
		fetches = new ArrayList<>();
		fetch = new Fetch();
		fetch.setUri("uri");
		fetches.add(fetch);
		
		constraints = new ArrayList<>();
		constraint = new Constraint();
		constraint.setValue("value");
		constraints.add(constraint);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(marathon.getLabels(), nullValue());
		assertThat(marathon.getHealthChecks(), nullValue());
		assertThat(marathon.getSecrets(), nullValue());
		assertThat(marathon.getFetch(), nullValue());
		assertThat(marathon.getConstraints(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		marathon.labels = labels;		
		assertThat(marathon.getLabels(), not(nullValue()));
		assertThat(marathon.getLabels(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(marathon.getLabels(), not(sameInstance(labels)));
		
		marathon.healthChecks = healthChecks;		
		assertThat(marathon.getHealthChecks(), not(nullValue()));
		assertThat(marathon.getHealthChecks(), hasItem(hasProperty("portIndex", equalTo(1))));
		assertThat(marathon.getHealthChecks(), not(sameInstance(healthChecks)));
		
		marathon.secrets = secrets;		
		assertThat(marathon.getSecrets(), not(nullValue()));
		assertThat(marathon.getSecrets(), hasItem(hasProperty("name", equalTo("name"))));
		assertThat(marathon.getSecrets(), not(sameInstance(secrets)));
		
		marathon.fetch = fetches;		
		assertThat(marathon.getFetch(), not(nullValue()));
		assertThat(marathon.getFetch(), hasItem(hasProperty("uri", equalTo("uri"))));
		assertThat(marathon.getFetch(), not(sameInstance(fetches)));
		
		marathon.constraints = constraints;		
		assertThat(marathon.getConstraints(), not(nullValue()));
		assertThat(marathon.getConstraints(), hasItem(hasProperty("value", equalTo("value"))));
		assertThat(marathon.getConstraints(), not(sameInstance(constraints)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		marathon.setLabels(null);
		assertThat(marathon.getLabels(), not(nullValue()));
		assertThat(marathon.getLabels(), hasSize(0));
		
		marathon.setHealthChecks(null);
		assertThat(marathon.getHealthChecks(), not(nullValue()));
		assertThat(marathon.getHealthChecks(), hasSize(0));
		
		marathon.setSecrets(null);
		assertThat(marathon.getSecrets(), not(nullValue()));
		assertThat(marathon.getSecrets(), hasSize(0));
		
		marathon.setFetch(null);
		assertThat(marathon.getFetch(), not(nullValue()));
		assertThat(marathon.getFetch(), hasSize(0));
		
		marathon.setConstraints(null);
		assertThat(marathon.getConstraints(), not(nullValue()));
		assertThat(marathon.getConstraints(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		marathon.setLabels(labels);
		assertThat(marathon.labels, not(nullValue()));
		assertThat(marathon.labels, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(marathon.labels, not(sameInstance(labels)));
		
		marathon.setHealthChecks(healthChecks);
		assertThat(marathon.healthChecks, not(nullValue()));
		assertThat(marathon.healthChecks, hasItem(hasProperty("portIndex", equalTo(1))));
		assertThat(marathon.healthChecks, not(sameInstance(healthChecks)));
		
		marathon.setSecrets(secrets);
		assertThat(marathon.secrets, not(nullValue()));
		assertThat(marathon.secrets, hasItem(hasProperty("name", equalTo("name"))));
		assertThat(marathon.secrets, not(sameInstance(secrets)));
		
		marathon.setFetch(fetches);
		assertThat(marathon.fetch, not(nullValue()));
		assertThat(marathon.fetch, hasItem(hasProperty("uri", equalTo("uri"))));
		assertThat(marathon.fetch, not(sameInstance(fetches)));
		
		marathon.setConstraints(constraints);
		assertThat(marathon.constraints, not(nullValue()));
		assertThat(marathon.constraints, hasItem(hasProperty("value", equalTo("value"))));
		assertThat(marathon.constraints, not(sameInstance(constraints)));
	}
}
