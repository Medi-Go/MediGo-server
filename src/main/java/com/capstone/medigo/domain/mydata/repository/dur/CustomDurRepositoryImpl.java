package com.capstone.medigo.domain.mydata.repository.dur;

import static com.capstone.medigo.domain.mydata.model.QDur.*;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Dur;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomDurRepositoryImpl implements CustomDurRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Dur> findByMedicineInfo(MedicineInfo medicineInfo) {
		return jpaQueryFactory
			.selectFrom(dur)
			.where(dur.medicineInfo.eq(medicineInfo))
			.fetch();
	}
}
