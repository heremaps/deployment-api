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
import com.here.deployment.cloudconfig.model.dcos.Label;
import com.here.deployment.cloudconfig.model.dcos.PortMapping;

@RunWith(SpringRunner.class)
public class PortMappingTest {
	
	PortMapping portMapping;
	
	ArrayList<Label> labels;
	
	Label label;
	
	@Before
	public void setUp() {
		portMapping = new PortMapping();
		labels = new ArrayList<>();
		label = new Label();
		label.setKey("key");	
		labels.add(label);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(portMapping.getLabels(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		portMapping.labels = labels;		
		assertThat(portMapping.getLabels(), not(nullValue()));
		assertThat(portMapping.getLabels(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(portMapping.getLabels(), not(sameInstance(labels)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		portMapping.setLabels(null);
		assertThat(portMapping.getLabels(), not(nullValue()));
		assertThat(portMapping.getLabels(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		portMapping.setLabels(labels);
		assertThat(portMapping.labels, not(nullValue()));
		assertThat(portMapping.labels, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(portMapping.labels, not(sameInstance(labels)));
	}
}
