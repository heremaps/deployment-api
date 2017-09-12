package com.here.deployment.mapper.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.cloudconfig.model.dcos.Constraint;
import com.here.deployment.mapper.ConstraintMapper;
import com.here.deployment.mapper.impl.ConstraintMapperImpl;

@RunWith(SpringRunner.class)
public class ConstraintMapperImplTest {
	ConstraintMapper constraintMapper;
	
	@Before
	public void setUp() {
		constraintMapper = new ConstraintMapperImpl();
	}
	
	@Test
	public void test_null_input() {
		List<List<String>> result = constraintMapper.convert(null);
		
		assertThat(result, nullValue());
	}
	
	@Test
	public void test_empty_constraint() {
		Constraint constraint = new Constraint();		
		List<List<String>> result = constraintMapper.convert(Arrays.asList(constraint));
		List<String> innerList = new ArrayList<String>(3);
		innerList.add("");
		innerList.add("");
		innerList.add("");
		List<List<String>> outerList = new ArrayList<List<String>>();
		outerList.add(innerList);
		
		assertThat(result, not(nullValue()));
		assertThat(result, equalTo(outerList));
		
	}
}
