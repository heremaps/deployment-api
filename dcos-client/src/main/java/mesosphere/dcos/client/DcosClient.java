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
package mesosphere.dcos.client;

import static java.util.Arrays.asList;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import mesosphere.dcos.client.exception.DcosException;
import mesosphere.dcos.client.model.v2.Auth;
import mesosphere.dcos.client.model.v2.Token;
import mesosphere.dcos.client.utils.AuthHeadersInterceptor;
import mesosphere.dcos.client.utils.ModelUtils;

/**
 * The Class DcosClient.
 */
public class DcosClient {
	
	/** The Constant DCOS_MARATHON_URL. */
	private static final String DCOS_MARATHON_URL = "/service/marathon/";
	
	/**
	 * The Class MarathonHeadersInterceptor.
	 */
	static class MarathonHeadersInterceptor implements RequestInterceptor {
		
		/* (non-Javadoc)
		 * @see feign.RequestInterceptor#apply(feign.RequestTemplate)
		 */
		@Override
		public void apply(RequestTemplate template) {
			template.header("Accept", "application/json");
			template.header("Content-Type", "application/json");
		}
	}
	
	/**
	 * The Class MarathonErrorDecoder.
	 */
	static class MarathonErrorDecoder implements ErrorDecoder {
		
		/* (non-Javadoc)
		 * @see feign.codec.ErrorDecoder#decode(java.lang.String, feign.Response)
		 */
		@Override
		public Exception decode(String methodKey, Response response) {
			return new DcosException(response.status(), response.reason());
		}
	}

	/**
	 * Gets the single instance of DcosClient.
	 *
	 * @param endpoint the endpoint
	 * @param username the username
	 * @param password the password
	 * @return single instance of DcosClient
	 * @throws DcosException the dcos exception
	 */
	public static Dcos getInstance(String endpoint, String username, String password) throws DcosException {
		Dcos authDcos = getInstance(endpoint);		
		Token token = authDcos.getToken(new Auth(username, password));
		return getInstance(endpoint + DCOS_MARATHON_URL, new AuthHeadersInterceptor(token.getToken()));
	}

	/**
	 * Gets the single instance of DcosClient.
	 *
	 * @param endpoint the endpoint
	 * @param interceptors the interceptors
	 * @return single instance of DcosClient
	 */
	private static Dcos getInstance(String endpoint, RequestInterceptor... interceptors) {
		Builder b = Feign.builder()				
				//.logLevel(Level.FULL)
				//.logger(new Slf4jLogger())
				.encoder(new GsonEncoder(ModelUtils.GSON))
				.decoder(new GsonDecoder(ModelUtils.GSON))
				.errorDecoder(new MarathonErrorDecoder());
		if (interceptors != null) {
			b.requestInterceptors(asList(interceptors));
		}
		b.requestInterceptor(new MarathonHeadersInterceptor());
		return b.target(Dcos.class, endpoint);
	}
}
