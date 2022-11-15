package com.capstone.medigo.domain.mydata.service.dto.innerdto;

import java.util.StringJoiner;

public record Treatment (
	String treatType,
	String treatPersonName,
	String treatMedicalName
){
	@Override
	public String toString() {
		return new StringJoiner(", ", Treatment.class.getSimpleName() + "[", "]")
			.add("treatType='" + treatType + "'")
			.add("treatPersonName='" + treatPersonName + "'")
			.add("treatMedicalName='" + treatMedicalName + "'")
			.toString();
	}
}
