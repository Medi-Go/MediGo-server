package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import lombok.Builder;

public record MainMedicine(
	Long medicineId,
	String medicineName,
	int remainCount,
	int treatDate
) {

	@Builder
	public MainMedicine {
	}
}
