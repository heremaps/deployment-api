package com.here.deployment.configproperties;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.configproperties.LabelTemplateProperties;

@RunWith(SpringRunner.class)
public class LabelTemplatePropertiesTest {	
	LabelTemplateProperties labelTemplateProperties;

	@Before
	public void setUp() {
		labelTemplateProperties = new LabelTemplateProperties();
		
		Map<String, String> user = new HashMap<>();
		user.put("user", "value");
		user.put("override", "value2");
		labelTemplateProperties.setUser(Collections.unmodifiableMap(user));
		
		Map<String, String> provided = new HashMap<>();
		provided.put("provided", "value");
		provided.put("override", "value1");
		labelTemplateProperties.setProvided(Collections.unmodifiableMap(provided));
	}
	
	@Test
	public void test_get_provided() {
		assertThat(labelTemplateProperties.get("provided"), equalTo("value"));
	}
	
	@Test
	public void test_get_provided_no_user() {
		labelTemplateProperties.setUser(null);
		assertThat(labelTemplateProperties.get("provided"), equalTo("value"));
	}
	
	@Test
	public void test_get_user() {
		assertThat(labelTemplateProperties.get("user"), equalTo("value"));
	}
	
	@Test
	public void test_get_user_override_provided() {
		assertThat(labelTemplateProperties.get("override"), equalTo("value2"));
	}
}
