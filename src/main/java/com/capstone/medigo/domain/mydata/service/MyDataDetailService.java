package com.capstone.medigo.domain.mydata.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailPrescription;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailRequest;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetail;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;
import com.capstone.medigo.global.error.exception.PrescriptionException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataDetailService {
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MyDataDetail getMyDataInfo(Long memberId, LocalDateTime time, int month) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		int beforeTime = LocalDateTimeUtil.localTo8format(time.minusMonths(month));
		List<Prescription> prescriptions = prescriptionRepository.findByMemberAfterTime(member, beforeTime);

		List<DetailPrescriptionCase> detailPrescriptionCaseList = new ArrayList<>();
		for (Prescription prescription : prescriptions) {
			List<DetailMedicine> detailList = medicineRepository.findByPrescription(prescription).stream()
				.map((MyDataConverter::toDetailMedicine))
				.toList();
			if(!detailList.isEmpty()){
				DetailPrescriptionCase detailPrescriptionCase = MyDataConverter.toDetailPrescription(prescription,detailList);
				detailPrescriptionCaseList.add(detailPrescriptionCase);
			}
		}

		return new MyDataDetail(detailPrescriptionCaseList);
	}

	@Transactional
	public void updateDetailOfPrescription(DetailRequest detailRequest, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});
		member.changeMyDataDetailUpdateTime(LocalDateTime.now());

		List<DetailPrescription> prescriptions = detailRequest.prescriptions();
		for (DetailPrescription prescription : prescriptions) {
			Long prescriptionId = prescription.prescriptionId();
			Prescription prescriptionById = prescriptionRepository.findById(prescriptionId).orElseThrow(() -> {
				throw PrescriptionException.notFoundPrescription(prescriptionId);
			});

			prescriptionById.changeDetail(prescription.administerInterval(), prescription.dailyCount(), prescription.totalDayCount());
		}
	}
}
