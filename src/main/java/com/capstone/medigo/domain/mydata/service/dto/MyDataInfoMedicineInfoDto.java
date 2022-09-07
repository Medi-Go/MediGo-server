package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import lombok.Builder;

public record MyDataInfoMedicineInfoDto(
	String makingCompany,
	String productName,
	String medicineGroup,
	String salesCompany,
	String payInfo,
	String administerPath,
	String shape,
	String singleYN,
	String specialYN,
	List<MyDataInfoIngredientDto> myDataInfoIngredientDtos,
	List<MyDataInfoKpicDto> myDataInfoKpicDtos,
	List<MyDataInfoDurDto> myDataInfoDurDtos
) {
	@Builder
	public MyDataInfoMedicineInfoDto {
	}
}
