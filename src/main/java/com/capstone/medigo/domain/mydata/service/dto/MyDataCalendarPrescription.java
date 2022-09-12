package com.capstone.medigo.domain.mydata.service.dto;

import java.util.List;

import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarPrescription;

public record MyDataCalendarPrescription(
	List<CalendarPrescription> calendarPrescriptions
){
}
