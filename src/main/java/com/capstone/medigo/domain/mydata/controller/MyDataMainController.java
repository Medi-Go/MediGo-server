package com.capstone.medigo.domain.mydata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.MyDataMainService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MyDataMainController {
	private final MyDataMainService myDataMainService;

	@GetMapping
	public ResponseEntity<MyDataMain> myDataMain(
		@AuthenticationPrincipal Long memberId
	) {
		MyDataMain myDataMain = myDataMainService.findMedicinesInUse(memberId);

		return ResponseEntity
			.ok()
			.body(myDataMain);
	}
}
