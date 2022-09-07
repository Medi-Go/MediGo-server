package com.capstone.medigo.domain.mydata.repository.ingredient;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Ingredient;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;

public interface CustomIngredientRepository {
	List<Ingredient> findByMedicineInfo(MedicineInfo medicineInfo);
}
