package com.capstone.medigo.domain.mydata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.service.dto.calendar.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.calendar.Treatment;
import com.capstone.medigo.domain.mydata.service.dto.main.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.service.dto.main.DuplicatedMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.DuplicatedMedicineCase;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMain;
import com.capstone.medigo.domain.mydata.service.dto.main.MainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.MedicineEffect;
import com.capstone.medigo.domain.mydata.service.dto.PrescriptionAndMedicine;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyDataConverter {

	public static DetailMedicine toDetailMedicine(Medicine medicine) {
		return DetailMedicine.builder()
			.medicineId(medicine.getId())
			.medicineName(medicine.getMedicineNm())
			.medicineEffect(medicine.getMedicineEffect())
			.administerCount(medicine.getAdministerCnt())
			.build();
	}

	public static DetailPrescriptionCase toDetailPrescription(Prescription prescription,
		List<DetailMedicine> detailMedicines) {
		return DetailPrescriptionCase.builder()
			.prescriptionId(prescription.getId())
			.treatType(prescription.getTreatType())
			.treatName(prescription.getTreatDsnm())
			.treatDate(prescription.getTreatDate())
			.treatMedicalName(prescription.getTreatMedicalnm())
			.medicineDetails(detailMedicines)
			.build();
	}

	public static MainMedicine toMainMedicine(Medicine medicine, int remainCount) {
		return MainMedicine.builder()
			.medicineId(medicine.getId())
			.medicineName(medicine.getMedicineNm())
			.treatDate(medicine.getTreatDate())
			.remainCount(remainCount)
			.build();
	}

	public static MyDataMain toMyDataDto(List<MedicineEffect> medicineEffects,
		Map<String, List<PrescriptionAndMedicine>> duplicatedMedicineMap) {
		List<DuplicatedMedicine> duplicatedMedicines = new ArrayList<>();
		for (String key : duplicatedMedicineMap.keySet()) {
			List<PrescriptionAndMedicine> prescriptionAndMedicineData = duplicatedMedicineMap.get(key);
			if (prescriptionAndMedicineData.size() > 1) {
				List<DuplicatedMedicineCase> myDateDuplicationMedicineDto = new ArrayList<>();
				String medicineName = prescriptionAndMedicineData.get(0).getMedicine().getMedicineNm();
				Long medicineId = prescriptionAndMedicineData.get(0).getMedicine().getId();
				for (PrescriptionAndMedicine oneData : prescriptionAndMedicineData) {

					myDateDuplicationMedicineDto.add(
						DuplicatedMedicineCase.builder()
							.treatDate(oneData.getTreatDate())
							.treatMedicalName(oneData.getTreatMedicalName())
							.administerInterval(oneData.getAdministerInterval())
							.dailyCount(oneData.getDailyCount())
							.totalDayCount(oneData.getTotalDayCount())
							.build());
				}
				DuplicatedMedicine duplicatedMedicine = new DuplicatedMedicine(medicineId, medicineName,
					myDateDuplicationMedicineDto);
				duplicatedMedicines.add(duplicatedMedicine);
			}
		}
		return new MyDataMain(medicineEffects, duplicatedMedicines);
	}

	public static Treatment toTreatment(Prescription prescription) {
		return new Treatment(
			prescription.getTreatType(),
			prescription.getTreatDsnm(),
			prescription.getTreatMedicalnm()
		);
	}

	public static MyDataCalendarTreatment toMyDataCalendarTreatment(Map<Integer, List<Treatment>> dateMap) {
		List<CalendarTreatment> calendarTreatments = new ArrayList<>();
		for (Integer date : dateMap.keySet()) {
			List<Treatment> treatments = dateMap.get(date);
			CalendarTreatment calendarTreatment = new CalendarTreatment(date, treatments);
			calendarTreatments.add(calendarTreatment);
		}
		return new MyDataCalendarTreatment(calendarTreatments);
	}
}
