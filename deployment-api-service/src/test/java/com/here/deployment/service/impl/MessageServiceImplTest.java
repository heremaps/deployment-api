package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.service.impl.MessageServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class MessageServiceImplTest {	
	
	@Autowired
	MessageServiceImpl messageService;		
	
	@Test
	public void deploymentConflict_test() {
		assertThat(messageService.deploymentConflict(), not(nullValue()));
	}

	@Test
	public void deploymentBadRequest_test() {
		assertThat(messageService.deploymentBadRequest(), not(nullValue()));
	}
}

