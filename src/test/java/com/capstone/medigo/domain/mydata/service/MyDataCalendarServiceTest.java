package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.config.ServiceTestConfig;
import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.Treatment;
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
		Member member = saveMember("test50@gmail.com", "test50");
		savePrescription(LocalDateTime.now(), member, "처방조제");
		savePrescription(LocalDateTime.now(), member, "외래진료");
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

	@Test
	void testGetCalendarPrescriptions() {
		// given
		Member member = saveMember("test77@test.com", "test77");
		LocalDateTime time = LocalDateTime.of(2022, 9, 10, 00, 00);
		savePrescription(time, member, "처방조제");
		savePrescription(time, member, "외래진료");
		int now = LocalDateTimeUtil.localTo8format(LocalDateTime.now().minusDays(5));
		Long date = Long.valueOf(now / 100);

		// when
		MyDataCalendarPrescription calendarPrescription = myDataCalendarService.getCalendarMedicines(member.getId(),
			date);

		// then
		List<CalendarPrescription> calendarPrescriptions = calendarPrescription.calendarPrescriptions();
		for (CalendarPrescription prescription : calendarPrescriptions) {
			System.out.println(prescription.date());
		}

		assertThat(calendarPrescriptions.size()).isEqualTo(6);

		CalendarPrescription calendarTreatment0 = calendarPrescriptions.get(0);
		List<DetailPrescriptionCase> prescriptions = calendarTreatment0.prescriptions();
		assertThat(prescriptions.size()).isEqualTo(1);

		DetailPrescriptionCase detailPrescriptionCase = prescriptions.get(0);
		assertAll(
			() -> assertThat(detailPrescriptionCase.treatType()).isEqualTo("처방조제"),
			() -> assertThat(detailPrescriptionCase.treatName()).isEqualTo("김철수")
		);

		List<DetailMedicine> detailMedicines = detailPrescriptionCase.medicineDetails();
		assertThat(detailMedicines.size()).isEqualTo(1);

		DetailMedicine detailMedicine = detailMedicines.get(0);
		assertAll(
			() -> assertThat(detailMedicine.medicineName()).isEqualTo("록사렉스캡슐75mg (LOXALEX CAP)"),
			() -> assertThat(detailMedicine.medicineEffect()).isEqualTo("소화성궤양용제"),
			() -> assertThat(detailMedicine.administerCount()).isEqualTo(3)
		);
	}

	private Member saveMember(String email, String nickname) {
		return memberRepository.save(
			Member.builder()
				.email(email)
				.profileImageUrl("www.s31.com")
				.nickName(nickname)
				.name("김철진")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
	}
}