package com.capstone.medigo.dbtest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {

	@GetMapping("/")
	public String hello() {
		return "hello world";
	}
}
