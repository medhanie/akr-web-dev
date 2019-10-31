package io.medhanie.akrweb.be.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController implements BaseController {

	@GetMapping("/greeting")
	public String greeting() {
		return "Application{user} is up.";
	}

	@GetMapping("/member/greeting")
	public String greetings() {
		return "Application{member} is running";
	}

}
