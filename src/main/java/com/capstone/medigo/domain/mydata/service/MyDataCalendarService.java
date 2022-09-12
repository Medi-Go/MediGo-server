package com.capstone.medigo.domain.mydata.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.Treatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataCalendarService {
	private final MemberRepository memberRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final MedicineRepository medicineRepository;

	@Transactional(readOnly = true)
	public MyDataCalendarTreatment getCalendarTreatments(Long memberId, Long date) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		int start = LocalDateTimeUtil.getStartDate(date);
		int end = LocalDateTimeUtil.getEndDate(date);
		List<Prescription> prescriptions = prescriptionRepository.findTreatmentByMemberAndTimeInterval(member, start,
			end);

		Map<Integer, List<Treatment>> dateMap = new HashMap<>();
		for (Prescription prescription : prescriptions) {
			int treatDate = prescription.getTreatDate();
			if (!dateMap.containsKey(treatDate)) {
				dateMap.put(treatDate, new ArrayList<>());
			}
			Treatment treatment = MyDataConverter.toTreatment(prescription);
			dateMap.get(treatDate).add(treatment);
		}

		return MyDataConverter.toMyDataCalendarTreatment(dateMap);
	}

	@Transactional(readOnly = true)
	public MyDataCalendarPrescription getCalendarMedicines(Long memberId, Long date) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});

		int dateWith8format = (int)(date * 100);
		List<Prescription> prescriptions = prescriptionRepository.findByEndDateWhichBiggerThanDate(
			member, dateWith8format);

		Map<Integer, List<DetailPrescriptionCase>> dateMap = new HashMap<>();
		for (Prescription prescription : prescriptions) {
			List<DetailMedicine> detailList = medicineRepository.findByPrescription(prescription).stream()
				.map((MyDataConverter::toDetailMedicine))
				.toList();
			DetailPrescriptionCase detailPrescriptionCase = MyDataConverter.toDetailPrescription(prescription,
				detailList);

			checkIntervalAndAddDateMap(dateWith8format, dateMap, prescription, detailPrescriptionCase);
		}

		return MyDataConverter.toMyDataCalendarPrescription(dateMap);
	}

	private void checkIntervalAndAddDateMap(int dateWith8format, Map<Integer, List<DetailPrescriptionCase>> dateMap,
		Prescription prescription, DetailPrescriptionCase detailPrescriptionCase) {
		LocalDateTime date = LocalDateTimeUtil.eightToLocalFormat(dateWith8format+1);
		LocalDateTime treatDate = LocalDateTimeUtil.eightToLocalFormat(prescription.getTreatDate());
		LocalDateTime endDate = LocalDateTimeUtil.eightToLocalFormat(prescription.getEndDate());
		int interval = prescription.getAdministerInterval();

		if(isDifferenceOver1Year(date, endDate, interval)){
			return;
		}

		while (endDate.isAfter(date)) {
			if (endDate.isBefore(treatDate)) {
				break;
			}

			if(isDifferentYearOrMonth(date, endDate)){
				endDate = endDate.minus(Period.ofDays(interval));
				continue;
			}

			int dateKey = LocalDateTimeUtil.localTo8format(endDate);
			if (!dateMap.containsKey(dateKey)) {
				dateMap.put(dateKey, new ArrayList<>());
			}
			dateMap.get(dateKey).add(detailPrescriptionCase);

			endDate = endDate.minus(Period.ofDays(interval));
		}
	}

	private boolean isDifferenceOver1Year(LocalDateTime date, LocalDateTime endDate, int interval) {
		return interval <= 0 || Period.between(date.toLocalDate(), endDate.toLocalDate()).getYears() >= 1;
	}

	private boolean isDifferentYearOrMonth(LocalDateTime date, LocalDateTime endDate) {
		return endDate.getYear() != date.getYear() || endDate.getMonth().getValue() != date.getMonth().getValue();
	}
}
