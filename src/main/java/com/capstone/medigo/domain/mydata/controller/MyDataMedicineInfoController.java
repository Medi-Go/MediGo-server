package com.capstone.medigo.domain.mydata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.MyDataMedicineInfoService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMedicineInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MyDataMedicineInfoController {
	private final MyDataMedicineInfoService myDataMedicineInfoService;

	@GetMapping("/{medicineId}")
	public ResponseEntity<MyDataMedicineInfo> medicineInfo(
		@PathVariable Long medicineId
	) {
		MyDataMedicineInfo medicineInfo = myDataMedicineInfoService.findMedicineInfo(medicineId);

		return ResponseEntity
			.ok()
			.body(medicineInfo);
	}
}
