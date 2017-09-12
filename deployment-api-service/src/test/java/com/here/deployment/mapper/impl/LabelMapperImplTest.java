package com.here.deployment.mapper.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.mapper.LabelMapper;
import com.here.deployment.mapper.impl.LabelMapperImpl;

@RunWith(SpringRunner.class)
public class LabelMapperImplTest {
	LabelMapper labelMapper;
	
	@Before
	public void setUp() {
		labelMapper = new LabelMapperImpl();
	}
	
	@Test
	public void test_null_input() {
		Map<String, String> result = labelMapper.convert(null);
		
		assertThat(result, nullValue());
	}
}
