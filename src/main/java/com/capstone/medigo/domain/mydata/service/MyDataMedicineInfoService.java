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
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoDurDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoIngredientDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoKpicDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoMedicineDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoMedicineInfoDto;
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
	public MyDataInfoMedicineDto findMedicineInfo(Long medicineId) {
		Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> {
			throw MedicineException.notFoundMedicine(medicineId);
		});

		List<MyDataInfoMedicineInfoDto> myDataInfoMedicineInfoDtoList = new ArrayList<>();
		List<MedicineInfo> medicineInfos = medicineInfoRepository.findByMedicine(medicine);

		for (MedicineInfo medicineInfo : medicineInfos) {
			List<MyDataInfoIngredientDto> myDataInfoIngredientDtos = makeIngredientDtos(medicineInfo);
			List<MyDataInfoKpicDto> myDataInfoKpicDtos = makeKpicDtos(medicineInfo);
			List<MyDataInfoDurDto> myDataInfoDurDtos = makeDurDtos(medicineInfo);

			makeMyDataInfoMedicineInfoDtoList(myDataInfoMedicineInfoDtoList, medicineInfo, myDataInfoIngredientDtos, myDataInfoKpicDtos,
				myDataInfoDurDtos);
		}

		return makeMyDataInfoMedicineDto(medicineId, medicine, myDataInfoMedicineInfoDtoList);
	}

	private List<MyDataInfoDurDto> makeDurDtos(MedicineInfo medicineInfo) {
		List<MyDataInfoDurDto> myDataInfoDurDtos = new ArrayList<>();
		List<Dur> durs = durRepository.findByMedicineInfo(medicineInfo);
		for (Dur dur : durs) {
			myDataInfoDurDtos.add(new MyDataInfoDurDto(dur.getAgeTaboo(), dur.getPregnantTaboo(), dur.getCombinedTaboo()));
		}

		return myDataInfoDurDtos;
	}

	private List<MyDataInfoKpicDto> makeKpicDtos(MedicineInfo medicineInfo) {
		List<MyDataInfoKpicDto> myDataInfoKpicDtos = new ArrayList<>();
		List<Kpic> kpics = kpicRepository.findByMedicineInfo(medicineInfo);
		for (Kpic kpic : kpics) {
			myDataInfoKpicDtos.add(new MyDataInfoKpicDto(kpic.getKpic()));
		}

		return myDataInfoKpicDtos;
	}

	private List<MyDataInfoIngredientDto> makeIngredientDtos(MedicineInfo medicineInfo) {
		List<Ingredient> ingredients = ingredientRepository.findByMedicineInfo(medicineInfo);
		List<MyDataInfoIngredientDto> myDataInfoIngredientDtos = new ArrayList<>();
		for (Ingredient ingredient : ingredients) {
			myDataInfoIngredientDtos.add(new MyDataInfoIngredientDto(ingredient.getIngredientName()));
		}

		return myDataInfoIngredientDtos;
	}

	private MyDataInfoMedicineDto makeMyDataInfoMedicineDto(Long medicineId, Medicine medicine,
		List<MyDataInfoMedicineInfoDto> myDataInfoMedicineInfoDtoList) {

		return MyDataInfoMedicineDto.builder()
			.medicineId(medicineId)
			.medicineName(medicine.getMedicineNm())
			.medicineEffect(medicine.getMedicineEffect())
			.myDataInfoMedicineInfoDtoList(myDataInfoMedicineInfoDtoList)
			.build();
	}

	private void makeMyDataInfoMedicineInfoDtoList(List<MyDataInfoMedicineInfoDto> myDataInfoMedicineInfoDtoList, MedicineInfo medicineInfo,
		List<MyDataInfoIngredientDto> myDataInfoIngredientDtos, List<MyDataInfoKpicDto> myDataInfoKpicDtos,
		List<MyDataInfoDurDto> myDataInfoDurDtos) {
		myDataInfoMedicineInfoDtoList.add(
			MyDataInfoMedicineInfoDto.builder()
			.makingCompany(medicineInfo.getMakingCompany())
			.productName(medicineInfo.getProductNm())
			.medicineGroup(medicineInfo.getMedicineGroup())
			.salesCompany(medicineInfo.getSalesCompany())
			.payInfo(medicineInfo.getPayInfo())
			.administerPath(medicineInfo.getAdministerPath())
			.shape(medicineInfo.getShape())
			.singleYN(medicineInfo.getSingleYn())
			.specialYN(medicineInfo.getSpecialYn())
			.myDataInfoDurDtos(myDataInfoDurDtos)
			.myDataInfoIngredientDtos(myDataInfoIngredientDtos)
			.myDataInfoKpicDtos(myDataInfoKpicDtos)
			.build()
		);
	}
}
