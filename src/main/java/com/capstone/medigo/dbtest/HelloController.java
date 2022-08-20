package com.capstone.medigo.dbtest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

	@GetMapping("/")
	public String hello() {
		log.info("info");
		log.error("error");
		log.debug("debug");

		return "hello world";
	}
}
