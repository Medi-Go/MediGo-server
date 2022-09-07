package com.capstone.medigo.domain.mydata.repository.kpic;

import java.util.List;

import com.capstone.medigo.domain.mydata.model.Kpic;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;

public interface CustomKpicRepository {
	List<Kpic> findByMedicineInfo(MedicineInfo medicineInfo);
}
