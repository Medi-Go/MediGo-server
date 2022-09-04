package com.capstone.medigo.domain.mydata.repository.medicine;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;

public interface CustomMedicineRepository {
	List<Medicine> findByPrescription(Prescription prescription);

}
