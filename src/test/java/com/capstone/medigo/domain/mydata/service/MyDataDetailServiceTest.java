package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.config.ServiceTestConfig;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailPrescription;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailRequest;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetail;
import com.capstone.medigo.domain.mydata.service.dto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;

@SpringBootTest
class MyDataDetailServiceTest extends ServiceTestConfig {
	@Autowired
	MyDataDetailService myDataDetailService;
	@Autowired
	PrescriptionRepository prescriptionRepository;

	@Test
	void testGetMyDataInfo() {
		// given
		LocalDateTime now = LocalDateTime.now();

		// when
		MyDataDetail myDataInfo = myDataDetailService.getMyDataInfo(member.getId(), LocalDateTime.now(), 2);

		// then
		assertThat(myDataInfo.prescriptions().size()).isEqualTo(1);

		DetailPrescriptionCase prescription = myDataInfo.prescriptions().get(0);
		assertAll(
			() -> assertThat(prescription.prescriptionId()).isNotNull(),
			() -> assertThat(prescription.treatType()).isEqualTo("처방조제"),
			() -> assertThat(prescription.treatName()).isEqualTo("김철수"),
			() -> assertThat(prescription.treatDate()).isEqualTo(LocalDateTimeUtil.localTo8format(now.minusDays(5))),
			() -> assertThat(prescription.treatMedicalName()).isEqualTo("한가람약국[남동구 남동대로]"),
			() -> assertThat(prescription.medicineDetails()).isNotNull()
		);

		DetailMedicine medicine = prescription.medicineDetails().get(0);
		assertAll(
			() -> assertThat(medicine.medicineId()).isNotNull(),
			() -> assertThat(medicine.medicineName()).isEqualTo("록사렉스캡슐75mg (LOXALEX CAP)"),
			() -> assertThat(medicine.medicineEffect()).isEqualTo("소화성궤양용제"),
			() -> assertThat(medicine.administerCount()).isEqualTo(3)
		);
	}

	@Test
	void testUpdateDetailOfPrescription() {
		// given
		DetailRequest detailRequest = new DetailRequest(
			new ArrayList<>(Arrays.asList(
				makeDetailPrescription(prescriptionBefore5day.getId(), 1, 2, 3),
				makeDetailPrescription(prescriptionBefore4Month.getId(), 4, 5, 6)
			))
		);

		// when
		myDataDetailService.updateDetailOfPrescription(detailRequest);

		// then
		Prescription prescription1 = prescriptionRepository.findById(prescriptionBefore5day.getId()).get();
		assertAll(
			() -> assertThat(prescription1.getAdministerInterval()).isEqualTo(1),
			() -> assertThat(prescription1.getDailyCount()).isEqualTo(2),
			() -> assertThat(prescription1.getTotalDayCount()).isEqualTo(3),
			() -> assertThat(prescription1.getEndDate()).isEqualTo(
				LocalDateTimeUtil.localTo8format(LocalDateTime.now().minusDays(5).plusDays(1 * 3))
			)
		);
		Prescription prescription2 = prescriptionRepository.findById(prescriptionBefore4Month.getId()).get();
		assertAll(
			() -> assertThat(prescription2.getAdministerInterval()).isEqualTo(4),
			() -> assertThat(prescription2.getDailyCount()).isEqualTo(5),
			() -> assertThat(prescription2.getTotalDayCount()).isEqualTo(6),
			() -> assertThat(prescription2.getEndDate()).isEqualTo(
				LocalDateTimeUtil.localTo8format(LocalDateTime.now().minusMonths(4).plusDays(4 * 6))
			)
		);
	}

	private DetailPrescription makeDetailPrescription(Long id, int interval, int dailyCount, int totalCount) {
		return DetailPrescription.builder()
			.prescriptionId(id)
			.administerInterval(interval)
			.dailyCount(dailyCount)
			.totalDayCount(totalCount)
			.build();
	}

}