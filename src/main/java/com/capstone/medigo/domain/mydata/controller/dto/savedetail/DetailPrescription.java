package com.capstone.medigo.domain.mydata.controller.dto.savedetail;

import javax.validation.constraints.NotNull;

import lombok.Builder;

public record DetailPrescription (
	@NotNull
	Long id,
	@NotNull
	int administerInterval,
	@NotNull
	int dailyCount,
	@NotNull
	int totalDayCount
){
	@Builder
	public DetailPrescription {
	}
}
