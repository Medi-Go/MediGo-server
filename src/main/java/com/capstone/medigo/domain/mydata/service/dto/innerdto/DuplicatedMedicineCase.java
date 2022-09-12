package com.capstone.medigo.domain.mydata.service.dto.innerdto;

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
