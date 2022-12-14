package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;

import lombok.Builder;

public record DetailPrescriptionCase(
	Long prescriptionId,
	String treatType,
	String treatName,
	int treatDate,
	String treatMedicalName,
	List<DetailMedicine> medicineDetails
){
	@Builder
	public DetailPrescriptionCase {
	}
}