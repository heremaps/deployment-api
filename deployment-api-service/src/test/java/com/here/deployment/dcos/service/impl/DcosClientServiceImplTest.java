package com.here.deployment.dcos.service.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.dcos.service.impl.DcosClientServiceImpl;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.model.v2.Token;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class DcosClientServiceImplTest {	
	
	@Autowired
	DcosClientServiceImpl dcosClientService;		
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
	
	@Test
	public void dcosClient_test() throws Exception {	
		int port = wireMockRule.port();
		
		final String url = "http://localhost:" + port;
		final String username = "username";
		final String password = "password";
		final String tokenString = "token";
		
		final Token token = new Token();
		token.setToken(tokenString);
		
		stubFor(post(urlEqualTo("/acs/api/v1/auth/login")).willReturn(aResponse()
			.withStatus(HttpStatus.OK.value())
			.withHeader("Content-Type", "text/plain")
			.withBody(new ObjectMapper().writeValueAsString(token))));
		
		Dcos dcos = dcosClientService.dcosClient(url, username, password);
		assertThat(dcos, not(nullValue()));
	}
}

