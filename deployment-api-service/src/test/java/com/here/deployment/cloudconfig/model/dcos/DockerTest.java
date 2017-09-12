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
import com.here.deployment.cloudconfig.model.dcos.Docker;
import com.here.deployment.cloudconfig.model.dcos.PortMapping;

@RunWith(SpringRunner.class)
public class DockerTest {
	
	Docker docker;
	
	ArrayList<PortMapping> portMappings;
	
	PortMapping portMapping;
	
	@Before
	public void setUp() {
		docker = new Docker();
		portMappings = new ArrayList<>();
		portMapping = new PortMapping();
		portMapping.setServicePort(1);	
		portMappings.add(portMapping);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(docker.getPortMappings(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		docker.portMappings = portMappings;		
		assertThat(docker.getPortMappings(), not(nullValue()));
		assertThat(docker.getPortMappings(), hasItem(hasProperty("servicePort", equalTo(1))));
		assertThat(docker.getPortMappings(), not(sameInstance(portMappings)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		docker.setPortMappings(null);
		assertThat(docker.getPortMappings(), not(nullValue()));
		assertThat(docker.getPortMappings(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		docker.setPortMappings(portMappings);
		assertThat(docker.portMappings, not(nullValue()));
		assertThat(docker.portMappings, hasItem(hasProperty("servicePort", equalTo(1))));
		assertThat(docker.portMappings, not(sameInstance(portMappings)));
	}
}
