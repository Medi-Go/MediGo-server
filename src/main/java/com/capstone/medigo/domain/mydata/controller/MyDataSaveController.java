package com.capstone.medigo.domain.mydata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.MyDataSaveService;
import com.capstone.medigo.domain.mydata.service.MyDataLoadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/connect")
@RequiredArgsConstructor
public class MyDataSaveController {
	private final MyDataLoadService myDataLoadService;
	private final MyDataSaveService myDataSaveService;

	@RequestMapping
	public ResponseEntity<Void> connect(@AuthenticationPrincipal Long memberId) {
		myDataLoadService.connectToMyData(memberId);

		return ResponseEntity
			.ok()
			.build();
	}

	@RequestMapping("/data")
	public ResponseEntity<Void> connectionData(@AuthenticationPrincipal Long memberId) {
		String memberMyData = myDataLoadService.getMemberMyData(memberId);
		myDataSaveService.save(memberMyData);

		return ResponseEntity
			.ok()
			.build();
	}
}
