package com.capstone.medigo.domain.mydata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.dur.DurRepository;
import com.capstone.medigo.domain.mydata.repository.ingredient.IngredientRepository;
import com.capstone.medigo.domain.mydata.repository.kpic.KpicRepository;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.medicineinfo.MedicineInfoRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.calendar.Treatment;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataCalendarService {
	private final MemberRepository memberRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;
	private final MedicineInfoRepository medicineInfoRepository;
	private final IngredientRepository ingredientRepository;
	private final KpicRepository kpicRepository;
	private final DurRepository durRepository;

	@Transactional(readOnly = true)
	public MyDataCalendarTreatment getCalendarTreatments(Long memberId, Long date) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		int start = LocalDateTimeUtil.getStartDate(date);
		int end = LocalDateTimeUtil.getEndDate(date);
		List<Prescription> prescriptions = prescriptionRepository.findTreatmentByMemberAndTimeInterval(member, start, end);

		Map<Integer, List<Treatment>> dateMap = new HashMap<>();
		for (Prescription prescription : prescriptions) {
			int treatDate = prescription.getTreatDate();
			if(!dateMap.containsKey(treatDate)){
				dateMap.put(treatDate, new ArrayList<>());
			}
			Treatment treatment = MyDataConverter.toTreatment(prescription);
			dateMap.get(treatDate).add(treatment);
		}

		return MyDataConverter.toMyDataCalendarTreatment(dateMap);
	}
}
