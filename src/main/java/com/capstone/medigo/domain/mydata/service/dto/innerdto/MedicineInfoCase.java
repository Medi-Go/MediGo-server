package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;

import lombok.Builder;

public record MedicineInfoCase(
	Long medicineId,
	String makingCompany,
	String productName,
	String medicineGroup,
	String salesCompany,
	String payInfo,
	String administerPath,
	String shape,
	String singleYN,
	String specialYN,
	List<IngredientInfo> ingredientInfos,
	List<KpicInfo> kpicInfos,
	List<DurInfo> durInfos
) {
	@Builder
	public MedicineInfoCase {
	}
}
