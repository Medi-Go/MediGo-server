package com.capstone.medigo.domain.mydata.repository;

import com.capstone.medigo.domain.mydata.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
