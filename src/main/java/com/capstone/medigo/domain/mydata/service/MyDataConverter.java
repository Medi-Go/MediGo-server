package com.capstone.medigo.domain.mydata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationCase;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationPrescriptionDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainInfoDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;
import com.capstone.medigo.domain.mydata.service.dto.PrescriptionAndMedicineData;

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

	public static MyDataDetailPrescription toMyDataTreat(Prescription prescription,
		List<MyDataDetailMedicine> detailList) {
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

	public static MyDataMainDto toMyDataDto(
		List<MyDataMainMedicines> myDataMainMedicines,
		Map<String, List<PrescriptionAndMedicineData>> duplicatedMedicineMap
	) {
		MyDataMainInfoDto myDataMainInfoDto = new MyDataMainInfoDto(myDataMainMedicines);
		List<MyDataDuplicationCase> myDataDuplicationCases = new ArrayList<>();
		for (String key : duplicatedMedicineMap.keySet()) {
			List<PrescriptionAndMedicineData> prescriptionAndMedicineData = duplicatedMedicineMap.get(key);
			if (prescriptionAndMedicineData.size() > 1) {
				List<MyDataDuplicationPrescriptionDto> myDateDuplicationMedicineDto = new ArrayList<>();
				String medicineName = prescriptionAndMedicineData.get(0).getMedicine().getMedicineNm();
				Long medicineId = prescriptionAndMedicineData.get(0).getMedicine().getId();
				for(PrescriptionAndMedicineData oneData:prescriptionAndMedicineData){

					myDateDuplicationMedicineDto.add(
						MyDataDuplicationPrescriptionDto.builder()
						.treatDate(oneData.getTreatDate())
						.treatMedicalName(oneData.getTreatMedicalName())
						.administerInterval(oneData.getAdministerInterval())
						.dailyCount(oneData.getDailyCount())
						.totalDayCount(oneData.getTotalDayCount())
						.build());
				}
				MyDataDuplicationCase myDataDuplicationCase = new MyDataDuplicationCase(medicineId, medicineName, myDateDuplicationMedicineDto);
				myDataDuplicationCases.add(myDataDuplicationCase);
			}
		}
		MyDataDuplicationDto myDataDuplicationDto = new MyDataDuplicationDto(myDataDuplicationCases);
		return new MyDataMainDto(myDataMainInfoDto, myDataDuplicationDto);
	}
}
