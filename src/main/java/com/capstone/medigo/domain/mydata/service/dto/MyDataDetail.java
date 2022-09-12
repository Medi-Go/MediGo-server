package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

public record MyDataDetail(
	List<DetailPrescriptionCase> prescriptions
){
}
