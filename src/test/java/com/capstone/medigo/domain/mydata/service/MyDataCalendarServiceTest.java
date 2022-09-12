package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.config.ServiceTestConfig;
import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.calendar.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.calendar.Treatment;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;

@SpringBootTest
class MyDataCalendarServiceTest extends ServiceTestConfig {
	@Autowired
	MyDataCalendarService myDataCalendarService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	void testGetCalendarTreatments() {
		// given
		Member member = saveMember();
		savePrescription(member, "처방조제");
		savePrescription(member, "외래진료");
		int now = LocalDateTimeUtil.localTo8format(LocalDateTime.now().minusDays(5));
		Long date = Long.valueOf(now / 100);

		// when
		MyDataCalendarTreatment myDataCalendarTreatment = myDataCalendarService.getCalendarTreatments(member.getId(),
			date);

		// then
		List<CalendarTreatment> calendarTreatments = myDataCalendarTreatment.calendarTreatments();
		assertThat(calendarTreatments.size()).isEqualTo(1);

		CalendarTreatment calendarTreatment = calendarTreatments.get(0);
		assertThat(calendarTreatment.date()).isEqualTo(now);
		assertThat(calendarTreatment.treatments().size()).isEqualTo(1);

		Treatment treatment = calendarTreatment.treatments().get(0);
		assertAll(
			() -> assertThat(treatment.treatType()).isEqualTo("외래진료"),
			() -> assertThat(treatment.treatPersonName()).isEqualTo("김철수"),
			() -> assertThat(treatment.treatMedicalName()).isEqualTo("한가람약국[남동구 남동대로]")
		);

	}

	private Member saveMember() {
		return memberRepository.save(
			Member.builder()
				.email("test50@gmail.com")
				.profileImageUrl("www.s31.com")
				.nickName("test50")
				.name("김철진")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
	}
}