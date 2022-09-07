package com.capstone.medigo.domain.mydata.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailRequest;
import com.capstone.medigo.domain.mydata.service.MyDataDetailService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/info-input")
@RequiredArgsConstructor
public class MyDataDetailController {
	private final MyDataDetailService myDataDetailService;

	@GetMapping("/{month}")
	public ResponseEntity<MyDataDetailDto> myDataDetail(
		@AuthenticationPrincipal Long memberId,
		@PathVariable int month
	) {
		MyDataDetailDto myDataDetailDto = myDataDetailService.getMyDataInfo(memberId, LocalDateTime.now(), month);

		return ResponseEntity
			.ok()
			.body(myDataDetailDto);
	}

	@PatchMapping
	public ResponseEntity<Void> myDataDetail(
		@Valid @RequestBody DetailRequest detailRequest
	) {
		myDataDetailService.updateDetailOfPrescription(detailRequest);

		return ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}
}
