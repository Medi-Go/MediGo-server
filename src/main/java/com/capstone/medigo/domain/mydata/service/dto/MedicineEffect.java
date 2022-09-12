package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MedicineEffect(
	String effect,
	List<MainMedicine> medicines
){
}
