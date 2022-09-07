package com.capstone.medigo.domain.mydata.repository.kpic;

import static com.capstone.medigo.domain.mydata.model.QKpic.*;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Kpic;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomKpicRepositoryImpl implements CustomKpicRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Kpic> findByMedicineInfo(MedicineInfo medicineInfo) {
		return jpaQueryFactory
			.selectFrom(kpic1)
			.where(kpic1.medicineInfo.eq(medicineInfo))
			.fetch();
	}
}
