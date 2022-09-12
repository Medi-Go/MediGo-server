package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record DuplicatedMedicine(
	Long medicineId,
	String medicineName,
	List<DuplicatedMedicineCase> duplicatedMedicineCases
) {
}
