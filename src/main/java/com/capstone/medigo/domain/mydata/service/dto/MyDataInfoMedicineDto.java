package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import lombok.Builder;

public record MyDataInfoMedicineDto(
	Long medicineId,
	String medicineName,
	String medicineEffect,
	List<MyDataInfoMedicineInfoDto> myDataInfoMedicineInfoDtoList
){
	@Builder
	public MyDataInfoMedicineDto {
	}
}
