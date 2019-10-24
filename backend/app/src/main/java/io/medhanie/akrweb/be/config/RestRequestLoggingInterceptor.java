package io.medhanie.akrweb.be.config;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RestRequestLoggingInterceptor implements ClientHttpRequestInterceptor {
	private static final Logger logger = LogManager.getLogger(RestRequestLoggingInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		String requestString = String.format(
				"\n\tRequest Method: %s,\n\tReqeust URI: %s,\n\tRequest Headers: %s,\n\tRequest Body: %s",
				request.getMethod(), request.getURI(), request.getHeaders(),
				new String(body, Charset.forName("UTF-8")));
		logger.info(requestString);

		ClientHttpResponse response = execution.execute(request, body);

		String responseString = String.format(
				"\n\tResponse Status Code: %s,\n\tResponse Headers: %s,\n\tResponse Body: %s", response.getStatusCode(),
				response.getHeaders(), StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
		logger.info(responseString);

		return response;
	}

}
