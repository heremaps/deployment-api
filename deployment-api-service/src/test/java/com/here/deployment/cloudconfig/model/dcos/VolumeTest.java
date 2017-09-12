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
import com.here.deployment.cloudconfig.model.dcos.Volume;
import com.here.deployment.cloudconfig.model.dcos.VolumeOption;

@RunWith(SpringRunner.class)
public class VolumeTest {
	
	Volume volume;
	
	ArrayList<VolumeOption> options;
	
	VolumeOption option;
	
	@Before
	public void setUp() {
		volume = new Volume();
		options = new ArrayList<>();
		option = new VolumeOption();
		option.setKey("key");	
		options.add(option);
	}
	
	@Test
	public void testDefensiveCopyGetNull() {
		assertThat(volume.getOptions(), nullValue());
	}
	
	@Test
	public void testDefensiveCopyGet() {	
		volume.options = options;		
		assertThat(volume.getOptions(), not(nullValue()));
		assertThat(volume.getOptions(), hasItem(hasProperty("key", equalTo("key"))));
		assertThat(volume.getOptions(), not(sameInstance(options)));
	}
	
	@Test
	public void testDefensiveCopySetNull() {
		volume.setOptions(null);
		assertThat(volume.getOptions(), not(nullValue()));
		assertThat(volume.getOptions(), hasSize(0));
	}
	
	@Test
	public void testDefensiveCopySet() {
		volume.setOptions(options);
		assertThat(volume.options, not(nullValue()));
		assertThat(volume.options, hasItem(hasProperty("key", equalTo("key"))));
		assertThat(volume.options, not(sameInstance(options)));
	}
}
