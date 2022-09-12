package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import com.capstone.medigo.domain.mydata.service.dto.calendar.CalendarTreatment;

public record MyDataCalendarTreatment(
	List<CalendarTreatment> calendarTreatments
) {
}
