package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MyDataMainMedicines (
	String effect,
	List<MyDataMainMedicine> medicines
){
}
