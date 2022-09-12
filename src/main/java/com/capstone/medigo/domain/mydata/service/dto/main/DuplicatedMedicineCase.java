package com.capstone.medigo.domain.mydata.service.dto.main;

import lombok.Builder;

public record DuplicatedMedicineCase(
	int treatDate,
	String treatMedicalName,
	int administerInterval,
	int dailyCount,
	int totalDayCount
) {
	@Builder
	public DuplicatedMedicineCase {
	}
}
