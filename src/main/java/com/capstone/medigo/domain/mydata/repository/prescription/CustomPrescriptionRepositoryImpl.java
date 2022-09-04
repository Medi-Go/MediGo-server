package com.capstone.medigo.domain.mydata.repository.prescription;

;
import static com.capstone.medigo.domain.mydata.model.QPrescription.*;

import java.util.List;
import java.util.Optional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomPrescriptionRepositoryImpl implements CustomPrescriptionRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Prescription> findPrescriptionAfterTime(int time, Member member) {
		return jpaQueryFactory
			.selectFrom(prescription)
			.where(
				prescription.treatDate.goe(time),
				prescription.member.eq(member)
			)
			.fetch();
	}
}
