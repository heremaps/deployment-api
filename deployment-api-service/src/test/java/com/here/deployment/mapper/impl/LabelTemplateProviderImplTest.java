
package com.here.deployment.mapper.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.mapper.impl.LabelTemplateProviderImpl;

/**
 * The Class CloudConfigMappingServiceImplTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class LabelTemplateProviderImplTest {

	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(LabelTemplateProviderImplTest.class);

	@Autowired
	LabelTemplateProviderImpl labelTemplateProvider;

	@Test
	public void parse_success()  {
		// from application-test.yml
		String testString = labelTemplateProvider.parse("user_test", Arrays.asList("a", "b", "c"));
		assertThat(testString, equalTo("test_{this}_works \r \\r \\ \n \r\n \\r\\n_a_c_{yes}_b_ok"));
	}
	
	@Test
	public void parse_successNoArgs()  {
		// from application-test.yml
		String testString = labelTemplateProvider.parse("user_test", null);
		assertThat(testString, equalTo("test_{this}_works \r \\r \\ \n \r\n \\r\\n_{0}_{2}_{yes}_{1}_ok"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parse_illegalargumentexception()  {
		labelTemplateProvider.parse("", null);
		fail();		
	}
	
	
}
