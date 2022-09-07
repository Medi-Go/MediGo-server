package com.capstone.medigo.domain.mydata.repository.dur;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Dur;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;

public interface CustomDurRepository {
	List<Dur> findByMedicineInfo(MedicineInfo medicineInfo);
}
