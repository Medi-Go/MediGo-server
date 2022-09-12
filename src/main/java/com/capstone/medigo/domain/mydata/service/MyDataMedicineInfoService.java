package com.capstone.medigo.domain.mydata.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.mydata.model.Dur;
import com.capstone.medigo.domain.mydata.model.Ingredient;
import com.capstone.medigo.domain.mydata.model.Kpic;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.MedicineInfo;
import com.capstone.medigo.domain.mydata.repository.dur.DurRepository;
import com.capstone.medigo.domain.mydata.repository.ingredient.IngredientRepository;
import com.capstone.medigo.domain.mydata.repository.kpic.KpicRepository;
import com.capstone.medigo.domain.mydata.repository.medicineinfo.MedicineInfoRepository;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.service.dto.main.KpicInfo;
import com.capstone.medigo.domain.mydata.service.dto.main.MedicineInfoCase;
import com.capstone.medigo.domain.mydata.service.dto.main.DurInfo;
import com.capstone.medigo.domain.mydata.service.dto.main.IngredientInfo;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMedicineInfo;
import com.capstone.medigo.global.error.exception.MedicineException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataMedicineInfoService {
	private final MedicineRepository medicineRepository;
	private final MedicineInfoRepository medicineInfoRepository;
	private final IngredientRepository ingredientRepository;
	private final KpicRepository kpicRepository;
	private final DurRepository durRepository;

	@Transactional(readOnly = true)
	public MyDataMedicineInfo findMedicineInfo(Long medicineId) {
		Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> {
			throw MedicineException.notFoundMedicine(medicineId);
		});

		List<MedicineInfoCase> medicineInfoCases = new ArrayList<>();
		List<MedicineInfo> medicineInfos = medicineInfoRepository.findByMedicine(
			medicine);

		for (MedicineInfo medicineInfo : medicineInfos) {
			List<IngredientInfo> ingredientInfos = makeIngredients(medicineInfo);
			List<KpicInfo> kpicInfos = makeKpics(medicineInfo);
			List<DurInfo> durInfos = makeDurs(medicineInfo);

			makeMedicineInfos(medicineInfoCases, medicineInfo, ingredientInfos, kpicInfos, durInfos);
		}

		return makeMyDataMedicineInfo(medicineId, medicine, medicineInfoCases);
	}

	private List<DurInfo> makeDurs(MedicineInfo medicineInfo) {
		List<DurInfo> durInfos = new ArrayList<>();
		List<Dur> durs = durRepository.findByMedicineInfo(medicineInfo);
		for (Dur dur : durs) {
			durInfos.add(new DurInfo(dur.getAgeTaboo(), dur.getPregnantTaboo(), dur.getCombinedTaboo()));
		}

		return durInfos;
	}

	private List<KpicInfo> makeKpics(MedicineInfo medicineInfo) {
		List<KpicInfo> kpicInfos = new ArrayList<>();
		List<Kpic> kpics = kpicRepository.findByMedicineInfo(medicineInfo);
		for (Kpic kpic : kpics) {
			kpicInfos.add(new KpicInfo(kpic.getKpic()));
		}

		return kpicInfos;
	}

	private List<IngredientInfo> makeIngredients(MedicineInfo medicineInfo) {
		List<Ingredient> ingredients = ingredientRepository.findByMedicineInfo(medicineInfo);
		List<IngredientInfo> ingredientInfos = new ArrayList<>();
		for (Ingredient ingredient : ingredients) {
			ingredientInfos.add(new IngredientInfo(ingredient.getIngredientName()));
		}

		return ingredientInfos;
	}

	private MyDataMedicineInfo makeMyDataMedicineInfo(Long medicineId, Medicine medicine,
		List<MedicineInfoCase> medicineInfoCases) {

		return MyDataMedicineInfo.builder()
			.medicineId(medicineId)
			.medicineName(medicine.getMedicineNm())
			.medicineEffect(medicine.getMedicineEffect())
			.medicineInfoCases(medicineInfoCases)
			.build();
	}

	private void makeMedicineInfos(List<MedicineInfoCase> medicineInfoCases, MedicineInfo medicineInfo,
		List<IngredientInfo> ingredientInfos, List<KpicInfo> kpicInfos, List<DurInfo> durInfos) {
		medicineInfoCases.add(
			MedicineInfoCase.builder()
				.makingCompany(medicineInfo.getMakingCompany())
				.productName(medicineInfo.getProductNm())
				.medicineGroup(medicineInfo.getMedicineGroup())
				.salesCompany(medicineInfo.getSalesCompany())
				.payInfo(medicineInfo.getPayInfo())
				.administerPath(medicineInfo.getAdministerPath())
				.shape(medicineInfo.getShape())
				.singleYN(medicineInfo.getSingleYn())
				.specialYN(medicineInfo.getSpecialYn())
				.durInfos(durInfos)
				.ingredientInfos(ingredientInfos)
				.kpicInfos(kpicInfos)
				.build()
		);
	}
}
