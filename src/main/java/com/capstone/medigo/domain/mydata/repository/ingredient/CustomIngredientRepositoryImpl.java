package com.capstone.medigo.domain.mydata.repository.ingredient;

import static com.capstone.medigo.domain.mydata.model.QIngredient.*;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Ingredient;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomIngredientRepositoryImpl implements CustomIngredientRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Ingredient> findByMedicineInfo(MedicineInfo medicineInfo) {
		return jpaQueryFactory
			.selectFrom(ingredient)
			.where(ingredient.medicineInfo.eq(medicineInfo))
			.fetch();
	}
}
