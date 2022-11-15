package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.StringJoiner;

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

	@Override
	public String toString() {
		return new StringJoiner(", ", MainMedicine.class.getSimpleName() + "[", "]")
			.add("medicineId=" + medicineId)
			.add("medicineName='" + medicineName + "'")
			.add("remainCount=" + remainCount)
			.add("treatDate=" + treatDate)
			.toString();
	}
}
