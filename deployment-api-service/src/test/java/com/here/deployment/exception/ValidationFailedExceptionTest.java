package com.here.deployment.exception;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.here.deployment.exception.ValidationFailedException;

@RunWith(JUnit4.class)
public class ValidationFailedExceptionTest {

	@Test
	public void test_default_constructor() {
		ValidationFailedException exception = new ValidationFailedException();
		assertThat(exception, not(nullValue()));
		assertThat(exception, instanceOf(RuntimeException.class));
		assertThat(exception.getValidationErrors(), nullValue());
	}
	
	@Test
	public void test_string_constructor() {
		ValidationFailedException exception = new ValidationFailedException("error!");
		assertThat(exception, not(nullValue()));
		assertThat(exception, instanceOf(RuntimeException.class));
		assertThat(exception.getMessage(), equalTo("error!"));
		assertThat(exception.getValidationErrors(), nullValue());
	}
	
	@Test
	public void test_string_list_constructor() {
		ValidationFailedException exception = new ValidationFailedException("error!", Arrays.asList("validation error!"));
		assertThat(exception, not(nullValue()));
		assertThat(exception, instanceOf(RuntimeException.class));
		assertThat(exception.getMessage(), equalTo("error!"));
		assertThat(exception.getValidationErrors(), hasSize(1));
	}
	
	@Test
	public void test_string_list_constructor_null() {
		List<String> nullList = null;
		ValidationFailedException exception = new ValidationFailedException("error!", nullList);
		assertThat(exception.getValidationErrors(), not(nullValue()));
		assertThat(exception.getValidationErrors(), hasSize(0));
	}
	
	@Test
	public void test_get_null_validation_errors() {
		ValidationFailedException exception = new ValidationFailedException();
		exception.validationErrors = null;
		assertThat(exception.getValidationErrors(), nullValue());
	}
}
