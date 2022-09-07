package com.capstone.medigo.domain.mydata.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;
import com.capstone.medigo.domain.mydata.service.dto.PrescriptionAndMedicineData;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataMainService {
	private final MemberRepository memberRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;

	@Transactional(readOnly = true)
	public MyDataMainDto findMedicinesInUse(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		HashMap<String, List<PrescriptionAndMedicineData>> duplicatedMedicineMap = new HashMap<>();

		List<Medicine> medicinesInUse = new ArrayList<>();
		Set<String> medicineEffect = new HashSet<>();
		int now = LocalDateTimeUtil.localTo8format(LocalDateTime.now());
		List<Prescription> prescriptions = prescriptionRepository.findByMemberAndEndDate(member, now);

		for (Prescription prescription : prescriptions) {
			List<Medicine> medicines = medicineRepository.findByPrescription(prescription);
			for (Medicine medicine : medicines) {
				checkDuplication(duplicatedMedicineMap, medicine, prescription);
				medicineEffect.add(medicine.getMedicineEffect());
				medicinesInUse.add(medicine);
			}
		}

		Iterator<String> iterator = medicineEffect.iterator();
		List<MyDataMainMedicines> myDataMainMedicines = new ArrayList<>();

		while (iterator.hasNext()) {
			String effectName = iterator.next();
			List<MyDataMainMedicine> medicines = new ArrayList<>();
			for (Medicine medicine : medicinesInUse) {
				if (medicine.getMedicineEffect().equals(effectName)) {
					calculateRemainCount(now, medicines, medicine);
				}
			}
			myDataMainMedicines.add(new MyDataMainMedicines(effectName, medicines));
		}

		return MyDataConverter.toMyDataDto(myDataMainMedicines, duplicatedMedicineMap);
	}

	private void calculateRemainCount(int now, List<MyDataMainMedicine> medicines, Medicine medicine) {
		Prescription prescription = medicine.getPrescription();
		int remainCount = (int)Math.ceil((prescription.getEndDate() - now) / prescription.getAdministerInterval());
		medicines.add(MyDataConverter.toMyDataMainMedicine(medicine, remainCount));
	}

	private void checkDuplication(HashMap<String, List<PrescriptionAndMedicineData>> duplicatedMedicineMap, Medicine medicine,
		Prescription prescription) {
		String medicineName = medicine.getMedicineNm();
		if (!duplicatedMedicineMap.containsKey(medicineName)) {
			duplicatedMedicineMap.put(medicineName, new ArrayList<>(
				List.of(makePrescriptionAndMedicineData(medicine, prescription))));
		} else {
			List<PrescriptionAndMedicineData> medicineList = duplicatedMedicineMap.get(medicineName);
			medicineList.add(makePrescriptionAndMedicineData(medicine, prescription));
			duplicatedMedicineMap.put(medicineName, medicineList);
		}
	}

	private PrescriptionAndMedicineData makePrescriptionAndMedicineData(Medicine medicine, Prescription prescription) {
		return PrescriptionAndMedicineData.builder()
			.treatDate(prescription.getTreatDate())
			.treatMedicalName(prescription.getTreatMedicalnm())
			.administerInterval(prescription.getAdministerInterval())
			.dailyCount(prescription.getDailyCount())
			.totalDayCount(prescription.getTotalDayCount())
			.medicine(medicine)
			.build();
	}

}
