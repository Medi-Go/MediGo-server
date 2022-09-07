package com.capstone.medigo.domain.mydata.repository.medicineinfo;

import static com.capstone.medigo.domain.mydata.model.QMedicineInfo.*;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMedicineInfoRepositoryImpl implements CustomMedicineInfoRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MedicineInfo> findByMedicine(Medicine medicine) {
		return jpaQueryFactory
			.selectFrom(medicineInfo)
			.where(medicineInfo.medicine.eq(medicine))
			.fetch();
	}
}
