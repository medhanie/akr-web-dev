package io.medhanie.akrweb.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import io.medhanie.akrweb.exception.ApplicationException;

@Controller
@RestController
public class ApplicationRestExceptionHandler {
	private static final Logger logger = LogManager.getLogger(ApplicationRestExceptionHandler.class);

	@ExceptionHandler({ DataAccessException.class, ApplicationException.class })
	public final ResponseEntity<Object> handleAllException(Exception ex, Object body, WebRequest request) {
		logger.error(() -> ex.getMessage());

		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.NOT_FOUND;

		if (ex instanceof DataAccessException) {
			status = HttpStatus.NOT_FOUND;
		} else if (ex instanceof ApplicationException) {
			status = HttpStatus.BAD_REQUEST;
		}

		return handleExceptionInternal(ex, body, headers, status, request);

	}

	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}

}
