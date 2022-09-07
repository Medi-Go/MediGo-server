package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.config.ServiceTestConfig;
import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;

@SpringBootTest
class MyDataMainServiceTest extends ServiceTestConfig {
	@Autowired
	MyDataMainService myDataMainService;

	@Test
	@DisplayName("main 화면에 데이터를 보여주는데 end date 기준으로 유무가 나뉜다")
	void testFindMedicinesInUse () {
	    // given
		Member newMember = makeAndSaveMember();
		Prescription prescription = makeAndSavePrescription(newMember, LocalDateTime.now());
		makeAndSaveMedicine(prescription, LocalDateTime.now());
		Prescription inValidPrescription = makeAndSavePrescription(newMember, LocalDateTime.now().minusDays(20));
		makeAndSaveMedicine(inValidPrescription, LocalDateTime.now().minusDays(20));

		// when
		MyDataMainDto medicinesInUse = myDataMainService.findMedicinesInUse(newMember.getId());

		// then
		List<MyDataMainMedicines> myDataMainMedicines = medicinesInUse.effectField();
		assertThat(myDataMainMedicines.size()).isEqualTo(1);
	}

	private Member makeAndSaveMember(){
		return memberRepository.save(
			Member.builder()
				.email("mydatamain@gmail.com")
				.profileImageUrl("www.s31.com")
				.nickName("mydatamain")
				.name("김철수")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
	}

	private Prescription makeAndSavePrescription(Member member, LocalDateTime time) {
		return prescriptionRepository.save(
			Prescription.builder()
			.member(member)
			.treatType("처방조제")
			.visitCnt("1")
			.treatDsnm("김철수")
			.treatDate(LocalDateTimeUtil.localTo8format(time))
			.medicineCnt("3")
			.treatdsgb("1")
			.prescribeCnt("1")
			.treatMedicalnm("한가람약국[남동구 남동대로]")
			.administerInterval(3)
			.totalDayCount(2)
			.endDate(LocalDateTimeUtil.localTo8format(time) + 10)
			.build());
	}

	private Medicine makeAndSaveMedicine(Prescription prescription, LocalDateTime time) {
		return medicineRepository.save(Medicine.builder()
			.prescription(prescription)
			.treatType("약국")
			.medicineNm("록사렉스캡슐75mg (LOXALEX CAP)")
			.treatDate(LocalDateTimeUtil.localTo8format(time))
			.medicineEffect("소화성궤양용제")
			.prescribeCnt("0")
			.administerCnt(3)
			.build());
	}
}