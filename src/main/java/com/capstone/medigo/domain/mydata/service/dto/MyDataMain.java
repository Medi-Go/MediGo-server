package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MyDataMain(
	List<MedicineEffect> medicineEffects,
	List<DuplicatedMedicine> duplicatedMedicines
){
}
