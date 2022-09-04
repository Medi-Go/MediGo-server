package com.capstone.medigo.domain.mydata.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailPrescription;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataDetailService {
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;
	private final MemberRepository memberRepository;

	public MyDataDetailDto getMyDataInfo(Long memberId, LocalDateTime time, int month) {
		Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		int beforeTime = LocalDateTimeUtil.change8format(time.minusMonths(month));
		List<Prescription> prescriptions = prescriptionRepository.findPrescriptionAfterTime(beforeTime,member);

		List<MyDataDetailPrescription> myDataDetailPrescriptionList = new ArrayList<>();
		for (Prescription prescription : prescriptions) {
			List<MyDataDetailMedicine> detailList = medicineRepository.findByPrescription(prescription).stream()
				.map((MyDataConverter::toMyDataDetail))
				.toList();
			if(!detailList.isEmpty()){
				MyDataDetailPrescription myDataDetailPrescription = MyDataConverter.toMyDataTreat(prescription,detailList);
				myDataDetailPrescriptionList.add(myDataDetailPrescription);
			}
		}
		return new MyDataDetailDto(myDataDetailPrescriptionList);
	}
}