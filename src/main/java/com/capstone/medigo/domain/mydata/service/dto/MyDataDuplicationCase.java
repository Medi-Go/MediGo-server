package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MyDataDuplicationCase(
	Long medicineId,
	String medicineName,
	List<MyDataDuplicationPrescriptionDto> myDateDuplicationMedicineDto
) {
}
