
package com.here.deployment.cloudconfig.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.here.deployment.UnitTestApplication;
import com.here.deployment.cloudconfig.exception.CloudConfigClientException;
import com.here.deployment.cloudconfig.service.impl.CloudConfigMappingServiceImpl;
import com.here.deployment.domain.AppConfig;

/**
 * The Class CloudConfigMappingServiceImplTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class CloudConfigMappingServiceImplTest {

	/** The Constant LOGGER. */
	static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigMappingServiceImplTest.class);

	CloudConfigMappingServiceImpl cloudConfigMappingService;

	static final String PROPERTY_SOURCE_A_KEY = "key1";
	static final String PROPERTY_SOURCE_A_VAL = "val1";
	static final String PROPERTY_SOURCE_B_KEY = "key2";
	static final String PROPERTY_SOURCE_B_VAL = "val2";
	
	@Before
	public void setUp() {
		cloudConfigMappingService = new CloudConfigMappingServiceImpl();
	}
	/**
	 * Convert properties to app config success.
	 */
	@Test
	public void convertPropertiesToAppConfig_success() {
		Properties fakeProperties = new Properties();
		fakeProperties.put(PROPERTY_SOURCE_A_KEY, PROPERTY_SOURCE_A_VAL);
		fakeProperties.put(PROPERTY_SOURCE_B_KEY, PROPERTY_SOURCE_B_VAL);
		AppConfig<FakeAppConfig> fakeAppConfig = cloudConfigMappingService.convertPropertiesToAppConfig(fakeProperties,
				FakeAppConfig.class);

		assertThat(fakeAppConfig, not(nullValue()));
		assertThat(fakeAppConfig.getConfig(), not(nullValue()));
		assertThat(fakeAppConfig.getConfig().getKey1(), equalTo(PROPERTY_SOURCE_A_VAL));
		assertThat(fakeAppConfig.getConfig().getKey2(), equalTo(PROPERTY_SOURCE_B_VAL));
	}

	/**
	 * Convert properties to app config exception.
	 * @throws IOException 
	 */
	@Test(expected = CloudConfigClientException.class)
	public void convertPropertiesToAppConfig_exception() throws IOException {
		JavaPropsMapper fakeJavaPropsMapper = mock(JavaPropsMapper.class);
		cloudConfigMappingService.setJavaPropsMapper(fakeJavaPropsMapper);
		
		when(fakeJavaPropsMapper.readValue(any(Properties.class), Matchers.<Class<FakeAppConfig>>any())).thenThrow(new IOException("error!"));
		
		cloudConfigMappingService.convertPropertiesToAppConfig(new Properties(),
				FakeAppConfig.class);

		fail();
	}
	

	static class FakeAppConfig {
		private String key1;
		private String key2;
		/**
		 * @return the key1
		 */
		public String getKey1() {
			return key1;
		}
		/**
		 * @param key1 the key1 to set
		 */
		public void setKey1(String key1) {
			this.key1 = key1;
		}
		/**
		 * @return the key2
		 */
		public String getKey2() {
			return key2;
		}
		/**
		 * @param key2 the key2 to set
		 */
		public void setKey2(String key2) {
			this.key2 = key2;
		}
		
	}


}
