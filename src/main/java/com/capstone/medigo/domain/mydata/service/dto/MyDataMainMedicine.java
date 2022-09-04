package com.capstone.medigo.domain.mydata.service.dto;

import lombok.Builder;

public record MyDataMainMedicine(
	Long id,
	String medicineName,
	int remainCount,
	int treatDate
) {

	@Builder
	public MyDataMainMedicine {
	}
}
