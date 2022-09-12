package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import lombok.Builder;

public record DetailMedicine(
	Long medicineId,
	String medicineName,
	String medicineEffect,
	int administerCount
) {
	@Builder
	public DetailMedicine {
	}
}
