package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import lombok.Builder;

public record MyDataDetailTreatment(
	Long prescriptionId,
	String treatType,
	String treatName,
	int treatDate,
	String treatMedicalName,
	List<MyDataDetailMedicine> medicineDetailList
){
	@Builder
	public MyDataDetailTreatment {
	}
}