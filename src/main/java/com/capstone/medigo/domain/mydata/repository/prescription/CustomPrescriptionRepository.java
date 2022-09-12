package com.capstone.medigo.domain.mydata.repository.prescription;

import java.util.List;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Prescription;

public interface CustomPrescriptionRepository {

	List<Prescription> findByMemberAfterTime(Member member, int time);

	List<Prescription> findByMemberAndEndDate(Member member, int now);

	List<Prescription> findTreatmentByMemberAndTimeInterval(Member member, int start, int end);

	List<Prescription> findByEndDateWhichBiggerThanDate(Member member, int date);

}
