package com.capstone.medigo.domain.mydata.service.dto;

import lombok.Builder;

public record MyDataDuplicationPrescriptionDto(
	int treatDate,
	String treatMedicalName,
	int administerInterval,
	int dailyCount,
	int totalDayCount
) {
	@Builder
	public MyDataDuplicationPrescriptionDto {
	}
}
