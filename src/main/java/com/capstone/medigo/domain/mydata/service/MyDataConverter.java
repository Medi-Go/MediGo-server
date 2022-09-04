package com.capstone.medigo.domain.mydata.service;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicine;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyDataConverter {

	public static MyDataDetailMedicine toMyDataDetail(Medicine medicine) {
		return MyDataDetailMedicine.builder()
			.medicineId(medicine.getId())
			.medicineName(medicine.getMedicineNm())
			.medicineEffect(medicine.getMedicineEffect())
			.administerCount(medicine.getAdministerCnt())
			.build();
	}

	public static MyDataDetailPrescription toMyDataTreat(Prescription prescription, List<MyDataDetailMedicine> detailList) {
		return MyDataDetailPrescription.builder()
			.prescriptionId(prescription.getId())
			.treatType(prescription.getTreatType())
			.treatName(prescription.getTreatDsnm())
			.treatDate(prescription.getTreatDate())
			.treatMedicalName(prescription.getTreatMedicalnm())
			.medicineDetailList(detailList)
			.build();
	}

	public static MyDataMainMedicine toMyDataMainMedicine(Medicine medicine, int remainCount) {
		return MyDataMainMedicine.builder()
			.id(medicine.getId())
			.medicineName(medicine.getMedicineNm())
			.treatDate(medicine.getTreatDate())
			.remainCount(remainCount)
			.build();
	}
}
