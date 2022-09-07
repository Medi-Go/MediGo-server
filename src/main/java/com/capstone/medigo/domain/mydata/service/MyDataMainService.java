package com.capstone.medigo.domain.mydata.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataMainService {
	private final MemberRepository memberRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;
	
	public MyDataMainDto findMedicinesInUse(Long memberId) {
		Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		List<Medicine> medicinesInUse = new ArrayList<>();
		Set<String> medicineEffect = new HashSet<>();
		int now = LocalDateTimeUtil.localTo8format(LocalDateTime.now());
		List<Prescription> prescriptions = prescriptionRepository.findByMemberAndEndDate(member, now);

		for (Prescription prescription : prescriptions) {
			List<Medicine> medicines = medicineRepository.findByPrescription(prescription);
			for (Medicine medicine : medicines) {
				medicineEffect.add(medicine.getMedicineEffect());
				medicinesInUse.add(medicine);
			}
		}

		Iterator<String> iterator = medicineEffect.iterator();
		List<MyDataMainMedicines> myDataMainMedicines = new ArrayList<>();

		while(iterator.hasNext()){
			String effectName = iterator.next();
			List<MyDataMainMedicine> medicines = new ArrayList<>();
			for (Medicine medicine : medicinesInUse) {
				if(medicine.getMedicineEffect().equals(effectName)){
					// 남은 횟수 구하는 로직
					Prescription prescription = medicine.getPrescription();
					prescription.getEndDate();
					int remainCount = (now - prescription.getTreatDate())/prescription.getAdministerInterval();

					medicines.add(MyDataConverter.toMyDataMainMedicine(medicine, remainCount));
				}
			}
			myDataMainMedicines.add(new MyDataMainMedicines(effectName, medicines));
		}

		return new MyDataMainDto(myDataMainMedicines);
	}
}
