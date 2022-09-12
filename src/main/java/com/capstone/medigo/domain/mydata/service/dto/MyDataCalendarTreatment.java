package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarTreatment;

public record MyDataCalendarTreatment(
	List<CalendarTreatment> calendarTreatments
) {
}
