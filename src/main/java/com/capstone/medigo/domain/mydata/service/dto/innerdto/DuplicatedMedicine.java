package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;
import java.util.StringJoiner;

public record DuplicatedMedicine(
	Long medicineId,
	String medicineName,
	List<DuplicatedMedicineCase> duplicatedMedicineCases
) {
	@Override
	public String toString() {
		return new StringJoiner(", ", DuplicatedMedicine.class.getSimpleName() + "[", "]")
			.add("medicineId=" + medicineId)
			.add("medicineName='" + medicineName + "'")
			.add("duplicatedMedicineCases=" + duplicatedMedicineCases)
			.toString();
	}
}
