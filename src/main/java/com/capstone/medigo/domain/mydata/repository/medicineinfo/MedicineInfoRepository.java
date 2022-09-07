package com.capstone.medigo.domain.mydata.repository.medicineinfo;

import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineInfoRepository extends JpaRepository<MedicineInfo, Long>, CustomMedicineInfoRepository{
}
