package com.capstone.medigo.domain.mydata.service.dto;

import com.capstone.medigo.domain.mydata.model.Medicine;

import lombok.Builder;
import lombok.Data;

@Data
public class PrescriptionAndMedicine {
	private int treatDate;
	private String treatMedicalName;
	private int administerInterval;
	private int dailyCount;
	private int totalDayCount;
	private Medicine medicine;

	@Builder
	public PrescriptionAndMedicine(int treatDate, String treatMedicalName, int administerInterval,
		int dailyCount,
		int totalDayCount, Medicine medicine) {
		this.treatDate = treatDate;
		this.treatMedicalName = treatMedicalName;
		this.administerInterval = administerInterval;
		this.dailyCount = dailyCount;
		this.totalDayCount = totalDayCount;
		this.medicine = medicine;
	}
}
