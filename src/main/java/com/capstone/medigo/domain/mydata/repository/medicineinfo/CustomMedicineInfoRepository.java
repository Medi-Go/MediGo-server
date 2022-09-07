package com.capstone.medigo.domain.mydata.repository.medicineinfo;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;

public interface CustomMedicineInfoRepository {
	List<MedicineInfo> findByMedicine(Medicine medicine);
}
