package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.List;

public record CalendarPrescription(
	int date,
	List<DetailPrescriptionCase> prescriptions
) {
}
