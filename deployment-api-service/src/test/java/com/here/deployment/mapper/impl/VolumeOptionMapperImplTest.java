package com.here.deployment.mapper.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.mapper.VolumeOptionMapper;
import com.here.deployment.mapper.impl.VolumeOptionMapperImpl;

@RunWith(SpringRunner.class)
public class VolumeOptionMapperImplTest {
	VolumeOptionMapper volumeOptionMapper;
	
	@Before
	public void setUp() {
		volumeOptionMapper = new VolumeOptionMapperImpl();
	}
	
	@Test
	public void test_null_input() {
		Map<String, String> result = volumeOptionMapper.convert(null);
		
		assertThat(result, nullValue());
	}
}
