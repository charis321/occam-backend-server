package com.charis.occam_spm_sys.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:5173")
public class HelloController {
	
	@GetMapping("/hello")
	public String hello() {
		return "<h1>hello</h1>";
	}
}
