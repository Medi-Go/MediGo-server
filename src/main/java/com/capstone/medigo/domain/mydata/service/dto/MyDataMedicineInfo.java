package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import lombok.Builder;

public record MyDataMedicineInfo(
	Long medicineId,
	String medicineName,
	String medicineEffect,
	List<MedicineInfoCase> medicineInfoCases
){
	@Builder
	public MyDataMedicineInfo {
	}
}
