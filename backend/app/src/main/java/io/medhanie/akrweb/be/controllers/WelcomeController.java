package io.medhanie.erient.be.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/greeting")
	public String greeting() {
		return "Application is up.";
	}
	
	@RequestMapping("/greetings")
	public String greetings() {
		return "Still Running ...";
	}


}
