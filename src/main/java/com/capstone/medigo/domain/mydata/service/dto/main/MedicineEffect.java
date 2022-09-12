package com.capstone.medigo.domain.mydata.service.dto.main;

import java.util.List;

public record MedicineEffect(
	String effect,
	List<MainMedicine> medicines
){
}
