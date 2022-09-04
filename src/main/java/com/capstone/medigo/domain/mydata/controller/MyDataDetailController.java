package com.capstone.medigo.domain.mydata.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.controller.dto.DetailMonthRequest;
import com.capstone.medigo.domain.mydata.service.MyDataDetailService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailTreatment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/info-input")
@RequiredArgsConstructor
public class MyDataDetailController {
	private final MyDataDetailService myDataDetailService;

	@GetMapping
	public ResponseEntity<List<MyDataDetailTreatment>> simpleMyDataInfo(
		@AuthenticationPrincipal Long memberId,
		@Valid @RequestBody DetailMonthRequest detailMonthRequest
	) {
		List<MyDataDetailTreatment> myDataInfo = myDataDetailService.getMyDataInfo(memberId, LocalDateTime.now(), detailMonthRequest.month());

		return ResponseEntity
			.ok()
			.body(myDataInfo);
	}

	@PostMapping
	public ResponseEntity<Void> inputMyDataInfo(
		@AuthenticationPrincipal Long memberId
	) {

		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}
}
