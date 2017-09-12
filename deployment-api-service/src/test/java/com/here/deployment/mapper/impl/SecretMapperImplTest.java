package com.here.deployment.mapper.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.mapper.SecretMapper;
import com.here.deployment.mapper.impl.SecretMapperImpl;
import mesosphere.dcos.client.model.v2.Secret;

@RunWith(SpringRunner.class)
public class SecretMapperImplTest {
	SecretMapper secretMapper;
	
	@Before
	public void setUp() {
		secretMapper = new SecretMapperImpl();
	}
	
	@Test
	public void test_null_input() {
		Map<String, Secret> result = secretMapper.convert(null);
		
		assertThat(result, nullValue());
	}
}
