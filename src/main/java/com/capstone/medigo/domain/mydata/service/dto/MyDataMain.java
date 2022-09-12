package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import com.capstone.medigo.domain.mydata.service.dto.main.DuplicatedMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.MedicineEffect;

public record MyDataMain(
	List<MedicineEffect> medicineEffects,
	List<DuplicatedMedicine> duplicatedMedicines
){
}
