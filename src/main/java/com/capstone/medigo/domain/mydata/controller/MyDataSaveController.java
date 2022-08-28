package com.capstone.medigo.domain.mydata.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.MyDataSaveService;
import com.capstone.medigo.domain.mydata.service.MyDataLoadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/connection")
@RequiredArgsConstructor
public class MyDataSaveController {
	private final MyDataLoadService myDataLoadService;
	private final MyDataSaveService myDataSaveService;
	
	@PostMapping
	public ResponseEntity<Void> connect(@AuthenticationPrincipal Long memberId) {
		Map<String, Object> responseInfo = myDataLoadService.connectToMyData(memberId);
		myDataLoadService.changeMember(memberId, responseInfo);

		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}

	@PostMapping("/data")
	public ResponseEntity<Void> loadMyData(@AuthenticationPrincipal Long memberId) {
		String memberMyData = myDataLoadService.getMemberMyData(memberId);
		myDataSaveService.save(memberMyData);

		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}

}
