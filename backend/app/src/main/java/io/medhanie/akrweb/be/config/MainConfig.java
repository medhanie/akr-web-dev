package io.medhanie.akrweb.be.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.medhanie.akrweb.be.interceptors.RequestInterceptor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableScheduling
@EnableSwagger2
public class MainConfig implements WebMvcConfigurer {
	private static final Logger logger = LogManager.getLogger(MainConfig.class);

	@Value("${akrweb.https.port}")
	private String httpsPort;

	@Value("${akrweb.http.port}")
	private String httpPort;

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(Integer.valueOf(httpPort));
		connector.setRedirectPort(Integer.valueOf(httpsPort));
		return connector;
	}

	@Bean
	public Gson objectMapper() throws Exception {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").serializeNulls().create();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(
				requestFactory);
		requestFactory.setOutputStreaming(false);
		restTemplate.setRequestFactory(bufferingClientHttpRequestFactory);

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (interceptors != null && !interceptors.isEmpty()) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RestRequestLoggingInterceptor());
		restTemplate.setInterceptors(interceptors);

		restTemplate.setMessageConverters(getMessageConverters());

		return restTemplate;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.addAll(getMessageConverters());
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();

		GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
		try {
			gsonHttpMessageConverter.setGson(objectMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		converters.add(gsonHttpMessageConverter);

		return converters;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
	}

}
