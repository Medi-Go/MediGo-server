package com.capstone.medigo.domain.mydata.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.MyDataCalendarService;
import com.capstone.medigo.domain.mydata.service.MyDataMainService;
import com.capstone.medigo.domain.mydata.service.MyDataSaveService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMain;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DuplicatedMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.MainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.MedicineEffect;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.Treatment;

@SpringBootTest
@Transactional
class MyDataTestControllerTest {
	@Autowired
	MyDataTestController myDataTestController;
	@Autowired
	MyDataSaveService myDataSaveService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MyDataMainService myDataMainService;
	@Autowired
	PrescriptionRepository prescriptionRepository;
	@Autowired
	MyDataCalendarService myDataCalendarService;

	@Test
	void senario () {
		// 1. 유저 저장 및 데이터 저장
		Member save = memberRepository.save(Member.builder()
			.email("dldydgns530@gmail.com")
			.profileImageUrl("https://lh3.googleusercontent.com/a-/AFdZucoPyEGN6Umu-dn2dwpvbdX_FMg6hzK0p3yVN_acNw=s96-c")
			.nickName("test2")
			.name("이용훈")
			.jumin("19971107")
			.carrier(Carrier.LG)
			.phoneNumber("01011113333")
			.build());

		save.changeMyDataLoadUpdateTime(LocalDateTime.of(1970,01,01,00,00,00));
		save.changeMyDataDetailUpdateTime(LocalDateTime.of(1970,01,01,00,00,00));

		String result = myDataTestController.getResult("20221103");

		myDataSaveService.save(result, save.getId());

		// 2. 복용 횟수 입력
		List<Prescription> prescriptions = prescriptionRepository.findByMemberAfterTime(save, 20221015);
		for (Prescription prescription : prescriptions) {
			// 2일에 한번, 하루 2번, 100일동안 = 총 100번
			prescription.changeDetail(1, 1, 100);
		}

		// 잘 저장되었는지 체크
		System.out.println("=====================");
		System.out.println("현재 복용중인 약물");
		MyDataMain medicinesInUse = myDataMainService.findMedicinesInUse(save.getId());
		List<MedicineEffect> medicineEffects = medicinesInUse.medicineEffects();
		for (MedicineEffect medicineEffect : medicineEffects) {
			System.out.println(medicineEffect.effect());
			List<MainMedicine> medicines = medicineEffect.medicines();
			for (MainMedicine medicine : medicines) {
				System.out.println(medicine);
			}
		}
		System.out.println("=====================");
		System.out.println("중복약물");
		List<DuplicatedMedicine> duplicatedMedicines = medicinesInUse.duplicatedMedicines();
		for (DuplicatedMedicine duplicatedMedicine : duplicatedMedicines) {
			System.out.println(duplicatedMedicine);
		}
		System.out.println("=====================");

		// 3. 캘린더 처방 날짜 확인
		System.out.println("=====================");
		System.out.println("캘린더 처방 날짜 확인");
		MyDataCalendarTreatment calendarTreatments = myDataCalendarService.getCalendarTreatments(save.getId(),
			202211L);
		List<CalendarTreatment> calendarTreatments1 = calendarTreatments.calendarTreatments();
		for (CalendarTreatment calendarTreatment : calendarTreatments1) {
			System.out.println(calendarTreatment.date());
			List<Treatment> treatments = calendarTreatment.treatments();
			for (Treatment treatment : treatments) {
				System.out.println(treatment);
			}
		}
		System.out.println("=====================");

		// 4. 캘린더 복용 날짜 확인
		System.out.println("=====================");
		System.out.println("캘린더 복용 날짜 확인(11월");
		MyDataCalendarPrescription calendarMedicines = myDataCalendarService.getCalendarMedicines(save.getId(),
			202211L);
		List<CalendarPrescription> calendarPrescriptions = calendarMedicines.calendarPrescriptions();
		int count = 0;
		for (CalendarPrescription calendarPrescription : calendarPrescriptions) {
			System.out.println(calendarPrescription.date());
			List<DetailPrescriptionCase> prescriptions1 = calendarPrescription.prescriptions();
			count++;
			if(count==1){
				for (DetailPrescriptionCase detailPrescriptionCase : prescriptions1) {
					System.out.println(detailPrescriptionCase);
				}
			}
		}
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("캘린더 복용 날짜 확인(12월");
		calendarMedicines = myDataCalendarService.getCalendarMedicines(save.getId(),
			202212L);
		calendarPrescriptions = calendarMedicines.calendarPrescriptions();
		for (CalendarPrescription calendarPrescription : calendarPrescriptions) {
			System.out.println(calendarPrescription.date());
			List<DetailPrescriptionCase> prescriptions1 = calendarPrescription.prescriptions();
			// for (DetailPrescriptionCase detailPrescriptionCase : prescriptions1) {
			// 	System.out.println(detailPrescriptionCase);
			// }
		}
		System.out.println("=====================");

		// 5 추가 데이터 입력
		result = myDataTestController.getResult("20221105");

		myDataSaveService.save(result, save.getId());
	}

}