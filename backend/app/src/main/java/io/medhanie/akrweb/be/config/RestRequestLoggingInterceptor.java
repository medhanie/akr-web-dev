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
				"%s\tRequest Method: %s,%s\tReqeust URI: %s,%s\tRequest Headers: %s, %x\tRequest Body: %s",
				System.lineSeparator(), request.getMethod(), System.lineSeparator(), request.getURI(),
				System.lineSeparator(), request.getHeaders(), System.lineSeparator(),
				new String(body, Charset.forName("UTF-8")));
		logger.info(requestString);

		ClientHttpResponse response = execution.execute(request, body);

		String responseString = String.format(
				"%s\tResponse Status Code: %s,%s\tResponse Headers: %s,%s\tResponse Body: %s", System.lineSeparator(),
				response.getStatusCode(), System.lineSeparator(), response.getHeaders(), System.lineSeparator(),
				StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
		logger.info(responseString);

		return response;
	}

}
