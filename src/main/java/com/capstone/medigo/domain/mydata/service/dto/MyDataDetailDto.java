package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MyDataDetailDto(
	List<MyDataDetailPrescription> prescriptions
){
}
