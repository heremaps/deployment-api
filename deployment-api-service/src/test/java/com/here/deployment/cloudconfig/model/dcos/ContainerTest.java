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
import com.here.deployment.cloudconfig.model.dcos.Container;
import com.here.deployment.cloudconfig.model.dcos.Volume;

@RunWith(SpringRunner.class)
public class ContainerTest {
	
	Container container;
	
	ArrayList<Volume> volumes;
	
	Volume volume;
	
	@Before
	public void setUp() {
		container = new Container();
		volumes = new ArrayList<>();
		volume = new Volume();
		volume.setId("id");	
		volumes.add(volume);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(container.getVolumes(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		container.volumes = volumes;		
		assertThat(container.getVolumes(), not(nullValue()));
		assertThat(container.getVolumes(), hasItem(hasProperty("id", equalTo("id"))));
		assertThat(container.getVolumes(), not(sameInstance(volumes)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		container.setVolumes(null);
		assertThat(container.getVolumes(), not(nullValue()));
		assertThat(container.getVolumes(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		container.setVolumes(volumes);
		assertThat(container.volumes, not(nullValue()));
		assertThat(container.volumes, hasItem(hasProperty("id", equalTo("id"))));
		assertThat(container.volumes, not(sameInstance(volumes)));
	}
}
