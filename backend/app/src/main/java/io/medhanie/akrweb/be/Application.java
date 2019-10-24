package io.medhanie.akrweb.be;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {
	private static Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Application Starting...");

		SpringApplication.run(Application.class, args);

		logger.info("Application Started.");
	}

}
