package io.medhanie.akrweb.be.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LogManager.getLogger(RequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info(() -> String.format("Request to: %s Referrer: %s", request.getRequestURI(),
				request.getHeader("referer")));
		return true;
	}

}