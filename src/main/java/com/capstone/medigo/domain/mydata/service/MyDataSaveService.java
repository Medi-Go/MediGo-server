package com.capstone.medigo.domain.mydata.service;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.controller.dto.save.DataSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.DurSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.IngredientSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.KPISaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.MedicineInfoSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.MedicineSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.MyDataSaveRequest;
import com.capstone.medigo.domain.mydata.controller.dto.save.PrescriptionSaveRequest;
import com.capstone.medigo.domain.mydata.model.Dur;
import com.capstone.medigo.domain.mydata.model.Ingredient;
import com.capstone.medigo.domain.mydata.model.Kpic;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.dur.DurRepository;
import com.capstone.medigo.domain.mydata.repository.ingredient.IngredientRepository;
import com.capstone.medigo.domain.mydata.repository.kpic.KpicRepository;
import com.capstone.medigo.domain.mydata.repository.medicineinfo.MedicineInfoRepository;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.error.exception.MemberException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MyDataSaveService {
	private final DurRepository durRepository;
	private final MemberRepository memberRepository;
	private final IngredientRepository ingredientRepository;
	private final KpicRepository kpicRepository;
	private final MedicineInfoRepository medicineInfoRepository;
	private final MedicineRepository medicineRepository;
	private final PrescriptionRepository prescriptionRepository;

	@Transactional
	public void save(String myDataValue, Long memberId) {
		ObjectMapper mapper = new ObjectMapper();
		MyDataSaveRequest myDataSaveRequest;

		try {
			myDataSaveRequest = mapper.readValue(myDataValue, MyDataSaveRequest.class);
		} catch (JsonProcessingException e) {
			throw MemberException.invalidJsonChanging(e.getMessage());
		}
		if (!myDataSaveRequest.result().equals("SUCCESS")) {
			throw MemberException.invalidMyDataLoading();
		}

		DataSaveRequest dataSaveRequest = myDataSaveRequest.data();
		savePrescription(dataSaveRequest.MEDICINELIST(), memberId);
	}

	private void savePrescription(ArrayList<PrescriptionSaveRequest> prescriptions, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw MemberException.notFoundMember(memberId);
		});
		int lastUpdateTime = LocalDateTimeUtil.localTo8format(member.getMyDataLoadUpdateTime());
		member.changeMyDataLoadUpdateTime(LocalDateTime.now());

		prescriptions.stream()
			.filter(prescriptionSaveRequest ->
				prescriptionSaveRequest.TREATDATE() > lastUpdateTime
			)
			.forEach(prescription -> {
					Prescription savedPrescription = prescriptionRepository.save(
						Prescription.builder()
							.member(member)
							.treatType(prescription.TREATTYPE())
							.visitCnt(prescription.VISITCNT())
							.treatDsnm(prescription.TREATDSNM())
							.treatDate(prescription.TREATDATE())
							.medicineCnt(prescription.MEDICINECNT())
							.treatdsgb(prescription.TREATDSGB())
							.prescribeCnt(prescription.PRESCRIBECNT())
							.treatMedicalnm(prescription.TREATMEDICALNM())
							.build()
					);
					saveMedicine(prescription.DETAILLIST(), savedPrescription);
				}
			);
	}

	private void saveMedicine(ArrayList<MedicineSaveRequest> medicines, Prescription prescription) {
		medicines.forEach(medicine -> {
			Medicine savedMedicine = medicineRepository.save(
				Medicine.builder()
					.prescription(prescription)
					.treatType(medicine.TREATTYPE())
					.medicineNm(medicine.MEDICINENM())
					.treatDate(medicine.TREATDATE())
					.medicineEffect(medicine.MEDICINEEFFECT())
					.prescribeCnt(medicine.PRESCRIBECNT())
					.administerCnt(medicine.ADMINISTERCNT())
					.build()
			);
			saveMedicineInfo(medicine.DRUGINFOLIST(), savedMedicine);

		});
	}

	private void saveMedicineInfo(ArrayList<MedicineInfoSaveRequest> medicineInfos, Medicine medicine) {
		medicineInfos.forEach(medicineInfo -> {
			MedicineInfo savedMedicineInfo = medicineInfoRepository.save(
				MedicineInfo.builder()
					.medicine(medicine)
					.makingCompany(medicineInfo.MAKINGCOMPANY())
					.productNm(medicineInfo.PRODUCTNM())
					.medicineGroup(medicineInfo.MEDICINEGROUP())
					.salesCompany(medicineInfo.SALESCOMPANY())
					.payInfo(medicineInfo.ADMINISTERPATH())
					.shape(medicineInfo.SHAPE())
					.singleYn(medicineInfo.SINGLEYN())
					.specialYn(medicineInfo.SPECIALYN())
					.build()
			);
			saveKPICs(medicineInfo.KPICLIST(), savedMedicineInfo);
			saveIngredients(medicineInfo.INGREDIENTNMLIST(), savedMedicineInfo);
			saveDurs(medicineInfo.DUR(), savedMedicineInfo);
		});

	}

	private void saveDurs(ArrayList<DurSaveRequest> durs, MedicineInfo medicineInfo) {
		durs.forEach(dur -> {
			durRepository.save(Dur.builder()
				.medicineInfo(medicineInfo)
				.ageTaboo(dur.AGETABOO())
				.pregnantTaboo(dur.PREGNANTTABOO())
				.combinedTaboo(dur.COMBINEDTABOO())
				.build()
			);
		});
	}

	private void saveIngredients(ArrayList<IngredientSaveRequest> ingredients, MedicineInfo medicineInfo) {
		ingredients.forEach(ingredient -> {
			ingredientRepository.save(new Ingredient(medicineInfo, ingredient.INGREDIENTNM()));
		});
	}

	private void saveKPICs(ArrayList<KPISaveRequest> KPIs, MedicineInfo medicineInfo) {
		KPIs.forEach(KPI -> {
			kpicRepository.save(new Kpic(medicineInfo, KPI.KPIC()));
		});
	}
}
