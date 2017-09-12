package com.here.deployment.service.impl;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.exception.ValidationFailedException;
import com.here.deployment.service.impl.BindingResultHandlerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class BindingResultHandlerServiceImplTest {	
	
	@Autowired
	BindingResultHandlerServiceImpl bindingResultHandlerService;
	
	@MockBean
	BindingResult bindingResult;

	@Test
	public void handleBindingResult_noErrors() {
		when(bindingResult.hasErrors()).thenReturn(false);
		bindingResultHandlerService.handleBindingResult(bindingResult);		
	}

	@Test
	public void handleBindingResult_withErrors() {
		when(bindingResult.hasErrors()).thenReturn(true);
		when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(new ObjectError("object1", "Error !"), new ObjectError("object2", "Another error!")));
		
		try {
			bindingResultHandlerService.handleBindingResult(bindingResult);
		} catch (ValidationFailedException e) {			
			assertThat(e.getValidationErrors(), hasSize(2));
		}
	}

}

