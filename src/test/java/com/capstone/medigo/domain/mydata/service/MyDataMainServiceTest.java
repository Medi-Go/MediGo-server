package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.config.ServiceTestConfig;
import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;

@SpringBootTest
class MyDataMainServiceTest extends ServiceTestConfig {
	@Autowired
	MyDataMainService myDataMainService;

	@Test
	void testFindMedicinesInUse () {
	    // given
		Member newMember = makeMember();
		Prescription prescription = makePrescription(newMember, LocalDateTime.now());

		// when
		MyDataMainDto medicinesInUse = myDataMainService.findMedicinesInUse(newMember.getId());

		// then
		List<MyDataMainMedicines> myDataMainMedicines = medicinesInUse.effectField();
		assertThat(myDataMainMedicines.size()).isEqualTo(1);
	}

	private Member makeMember(){
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

	private Prescription makePrescription(Member member, LocalDateTime time) {
		return Prescription.builder()
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
			.endDate(20220913)
			.build();
	}
}