package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;

public record CalendarTreatment(
	int date,
	List<Treatment> treatments
) {
}
