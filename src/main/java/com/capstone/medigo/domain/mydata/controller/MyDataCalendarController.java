package com.capstone.medigo.domain.mydata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.MyDataCalendarService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class MyDataCalendarController {
	private final MyDataCalendarService myDataCalendarService;

	@GetMapping("/treatments/{date}")
	public ResponseEntity<MyDataCalendarTreatment> calendarTreatments(
		@AuthenticationPrincipal Long memberId,
		@PathVariable Long date
	) {
		MyDataCalendarTreatment calendarTreatments = myDataCalendarService.getCalendarTreatments(memberId, date);

		return ResponseEntity
			.ok()
			.body(calendarTreatments);
	}

	@GetMapping("/prescriptions/{date}")
	public ResponseEntity<MyDataCalendarPrescription> calendarPrescriptions(
		@AuthenticationPrincipal Long memberId,
		@PathVariable Long date
	) {
		MyDataCalendarPrescription calendarMedicines = myDataCalendarService.getCalendarMedicines(memberId, date);

		return ResponseEntity
			.ok()
			.body(calendarMedicines);
	}
}
