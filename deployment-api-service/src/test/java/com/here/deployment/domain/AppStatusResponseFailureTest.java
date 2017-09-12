package com.here.deployment.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.domain.AppStatusResponseFailure;

@RunWith(SpringRunner.class)
public class AppStatusResponseFailureTest {
	
	@Test
	public void test_default_constructor() {
		AppStatusResponseFailure appStatusResponseFailure = new AppStatusResponseFailure();
		assertThat(appStatusResponseFailure, not(nullValue()));
		assertThat(appStatusResponseFailure.getErrors(), nullValue());
	}
	
	@Test
	public void test_error_constructor() {
		AppStatusResponseFailure appStatusResponseFailure = new AppStatusResponseFailure("error");
		assertThat(appStatusResponseFailure, not(nullValue()));
		assertThat(appStatusResponseFailure.getErrors(), hasSize(1));
	}
	
	@Test
	public void test_error_list_constructor() {
		AppStatusResponseFailure appStatusResponseFailure = new AppStatusResponseFailure(Arrays.asList("error1", "error2"));
		assertThat(appStatusResponseFailure, not(nullValue()));
		assertThat(appStatusResponseFailure.getErrors(), hasSize(2));
	}
	
	@Test
	public void test_error_constructor_nullList() {
		List<String> nullList = null;
		AppStatusResponseFailure appStatusResponseFailure = new AppStatusResponseFailure(nullList);
		assertThat(appStatusResponseFailure, not(nullValue()));
		assertThat(appStatusResponseFailure.getErrors(), hasSize(0));
	}

}
