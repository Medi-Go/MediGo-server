package com.capstone.medigo.domain.mydata.repository.ingredient;

import com.capstone.medigo.domain.mydata.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, CustomIngredientRepository {
}
