package mesosphere.dcos.client.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

// TODO: Auto-generated Javadoc
/**
 * Class that implements {@link RequestInterceptor} to provide authorization header with the configured token.
 */
public class AuthHeadersInterceptor implements RequestInterceptor {

	/** The header value. */
	private final String headerValue;

	/**
	 * Instantiates a new dcos headers interceptor.
	 *
	 * @param token the token
	 */
	public AuthHeadersInterceptor(String token) {
		this.headerValue = "token=" + token;
	}

	/* (non-Javadoc)
	 * @see feign.RequestInterceptor#apply(feign.RequestTemplate)
	 */
	@Override
	public void apply(RequestTemplate template) {
		template.header("Authorization", new String[]{this.headerValue});
	}
}

