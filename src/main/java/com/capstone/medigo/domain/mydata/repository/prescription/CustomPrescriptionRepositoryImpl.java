package com.capstone.medigo.domain.mydata.repository.prescription;

;
import static com.capstone.medigo.domain.mydata.model.QPrescription.*;

import java.util.List;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomPrescriptionRepositoryImpl implements CustomPrescriptionRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Prescription> findByMemberAfterTime(Member member, int time) {
		return jpaQueryFactory
			.selectFrom(prescription)
			.where(
				prescription.treatDate.goe(time),
				prescription.member.eq(member),
				prescription.isUpdate.eq(true)
			)
			.fetch();
	}

	@Override
	public List<Prescription> findByMemberAndEndDate(Member member, int now) {
		return jpaQueryFactory
			.selectFrom(prescription)
			.where(
				prescription.member.eq(member),
				prescription.endDate.goe(now),
				prescription.isUpdate.eq(true)
			)
			.fetch();
	}

	@Override
	public List<Prescription> findTreatmentByMemberAndTimeInterval(Member member, int start, int end) {
		return jpaQueryFactory
			.selectFrom(prescription)
			.where(
				prescription.member.eq(member),
				prescription.treatDate.between(start, end),
				prescription.isUpdate.eq(true),
				prescription.treatType.ne("처방조제")
			)
			.fetch();
	}

	@Override
	public List<Prescription> findByEndDateWhichBiggerThanDate(Member member, int date) {
		return jpaQueryFactory
			.selectFrom(prescription)
			.where(
				prescription.member.eq(member),
				prescription.isUpdate.eq(true),
				prescription.treatType.eq("처방조제"),
				prescription.endDate.goe(date)
			)
			.fetch();
	}

}
