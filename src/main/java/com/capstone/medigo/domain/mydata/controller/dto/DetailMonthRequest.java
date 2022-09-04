package com.capstone.medigo.domain.mydata.controller.dto;

import javax.validation.constraints.NotNull;

public record DetailMonthRequest(
	@NotNull
	int month
) {
}
