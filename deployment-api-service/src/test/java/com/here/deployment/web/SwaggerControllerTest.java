package com.here.deployment.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.here.deployment.UnitTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = { UnitTestApplication.class }, 
	webEnvironment = WebEnvironment.RANDOM_PORT
)
public class SwaggerControllerTest {
	
	static final Logger LOGGER = LoggerFactory.getLogger(SwaggerControllerTest.class);
	
	@Autowired
    TestRestTemplate restTemplate;
	
    @Test
    public void test_swagger_redirect() {
    	    
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/", String.class);
        
        assertThat(responseEntity.getStatusCode().value(), equalTo(302));
    }
    
    @Test
    public void test_swagger_endpoint() {
    	    
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/swagger-ui.html", String.class);
        
        assertThat(responseEntity.getStatusCode().value(), equalTo(200));
    }
    
   
}

