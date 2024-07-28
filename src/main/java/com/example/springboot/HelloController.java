package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "Hell33333";
	}

	@GetMapping("/something")
	public String lalalal() {
		return "asdasd";
	}

}
