/*
* Copyright (c) 2017 HERE Europe B.V.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.here.deployment.config;

import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
@Import({ springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class })
public class SwaggerConfig {
	
	/** The Constant DEPLOYMENT_DCOS_TAG. */
	public static final String DEPLOYMENT_DCOS_TAG = "DC/OS Deployments";

	/** The Constant API_TITLE. */
	static final String API_TITLE = "Deployment API";
	
	/** The Constant API_DESC. */
	static final String API_DESC = "REST-based deployment integration handler for DC/OS.";
	
	/** The Constant API_VERSION. */
	static final String API_VERSION = "1.0";
	
	/** The Constant API_TERMS_OF_SERVICE_URL. */
	static final String API_TERMS_OF_SERVICE_URL = "https://github.com/heremaps";
	
	/** The Constant API_CONTACT. */
	static final Contact API_CONTACT = new Contact("HERE Technologies", "https://github.com/heremaps", "micah.noland@here.com");
	
	/** The Constant API_LICENSE. */
	static final String API_LICENSE = "Apache 2.0";
	
	/** The Constant API_LICENSE_URL. */
	static final String API_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
	
	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.here.deployment.rest"))
					.paths(PathSelectors.any()) 
				.build()
					.directModelSubstitute(LocalDate.class, String.class)
					.genericModelSubstitutes(ResponseEntity.class)
					.apiInfo(apiInfo())
					.useDefaultResponseMessages(true);				
	}
	
	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private static ApiInfo apiInfo() {		
		return new ApiInfo(API_TITLE, API_DESC, API_VERSION, API_TERMS_OF_SERVICE_URL, API_CONTACT, API_LICENSE, API_LICENSE_URL);
	}
}