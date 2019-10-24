package io.medhanie.akrweb.be.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/greeting")
	public String greeting() {
		return "Application{user} is up.";
	}

	@RequestMapping("/member/greeting")
	public String greetings() {
		return "Application{member} is running";
	}

}
