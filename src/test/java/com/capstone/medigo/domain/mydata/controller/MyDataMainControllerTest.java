package com.capstone.medigo.domain.mydata.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.service.MyDataMainService;
import com.capstone.medigo.domain.mydata.service.dto.main.DuplicatedMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.DuplicatedMedicineCase;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMain;
import com.capstone.medigo.domain.mydata.service.dto.main.MainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.main.MedicineEffect;

@WebMvcTest(controllers = MyDataMainController.class)
class MyDataMainControllerTest extends TestConfig {
	@MockBean
	MyDataMainService myDataMainService;

	@Test
	@DisplayName("/api/v1/main 로 MyData 를 전달한다")
	void getMyDataMain() throws Exception {
		// given
		MyDataMain myDataMain = makeMyDataMain();
		given(myDataMainService.findMedicinesInUse(any())).willReturn(myDataMain);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/main")
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataMain)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("medicineEffects").type(ARRAY).description("효과에 해당하는 배열"),
					fieldWithPath("medicineEffects.[].effect").type(STRING).description("효과"),
					fieldWithPath("medicineEffects.[].medicines").type(ARRAY).description("효과에 해당하는 약물 배열"),
					fieldWithPath("medicineEffects.[].medicines.[].medicineId").type(NUMBER).description("약물 아이디"),
					fieldWithPath("medicineEffects.[].medicines.[].medicineName").type(STRING).description("약물 이름"),
					fieldWithPath("medicineEffects.[].medicines.[].remainCount").type(NUMBER).description("약물 남은 복용 횟수"),
					fieldWithPath("medicineEffects.[].medicines.[].treatDate").type(NUMBER).description("약물 처방 날짜"),
					fieldWithPath("duplicatedMedicines").type(ARRAY).description("중복약물 배열"),
					fieldWithPath("duplicatedMedicines.[].medicineId").type(NUMBER).description("중복약물 아이디"),
					fieldWithPath("duplicatedMedicines.[].medicineName").type(STRING).description("중복약물 이름"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases").type(ARRAY).description("중복약물 처방전 배열"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases.[].treatDate").type(NUMBER).description("해당 처방전 처방날짜"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases.[].treatMedicalName").type(STRING).description("해당 병원 이름"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases.[].administerInterval").type(NUMBER).description("투약기간"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases.[].dailyCount").type(NUMBER).description("하루 복용 횟수"),
					fieldWithPath("duplicatedMedicines.[].duplicatedMedicineCases.[].totalDayCount").type(NUMBER).description("하루기준 총 투약 횟수")
				)));
	}

	private MyDataMain makeMyDataMain() {
		MyDataMain myDataMain = new MyDataMain(
			new ArrayList<>(
				Arrays.asList(new MedicineEffect("소화성궤양용제", new ArrayList<MainMedicine>(
						Arrays.asList(
							new MainMedicine(1L, "약이름1", 3, 20220901),
							new MainMedicine(2L, "약이름2", 2, 20220901),
							new MainMedicine(3L, "약이름3", 1, 20220827)
						))),
					new MedicineEffect("해열, 진통, 소염제", new ArrayList<MainMedicine>(
						Arrays.asList(
							new MainMedicine(4L, "약이름1", 3, 20220901),
							new MainMedicine(5L, "약이름5", 2, 20220901),
							new MainMedicine(6L, "약이름6", 1, 20220827)
						)))
				)
			),
			new ArrayList<>(
				Arrays.asList(
					new DuplicatedMedicine(1L, "약이름1",
						new ArrayList<>(
							Arrays.asList(
								new DuplicatedMedicineCase(20220901, "한길병원", 3, 2, 1),
								new DuplicatedMedicineCase(20220901, "다른동네 병원", 3, 2, 1)
							)
						)
					)
				)
			)
		);
		return myDataMain;
	}
}