package com.capstone.medigo.domain.mydata.controller.dto.savedetail;

import javax.validation.constraints.NotNull;

public record DetailMonthRequest(
	@NotNull
	int month
) {
}
