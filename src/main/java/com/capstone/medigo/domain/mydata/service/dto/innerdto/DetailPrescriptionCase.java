package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;
import java.util.StringJoiner;

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

	@Override
	public String toString() {
		return new StringJoiner(", ", DetailPrescriptionCase.class.getSimpleName() + "[", "]")
			.add("prescriptionId=" + prescriptionId)
			.add("treatType='" + treatType + "'")
			.add("treatName='" + treatName + "'")
			.add("treatDate=" + treatDate)
			.add("treatMedicalName='" + treatMedicalName + "'")
			.add("medicineDetails=" + medicineDetails)
			.toString();
	}
}