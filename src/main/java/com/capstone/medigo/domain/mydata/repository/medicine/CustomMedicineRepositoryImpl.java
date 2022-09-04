package com.capstone.medigo.domain.mydata.repository.medicine;

import static com.capstone.medigo.domain.mydata.model.QMedicine.*;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMedicineRepositoryImpl implements CustomMedicineRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Medicine> findByPrescription(Prescription prescription) {
		return jpaQueryFactory
			.selectFrom(medicine)
			.where(medicine.prescription.eq(prescription))
			.fetch();
	}
}
