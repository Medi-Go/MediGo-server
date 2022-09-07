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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.service.MyDataMainService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationCase;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDuplicationPrescriptionDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainInfoDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMainMedicines;

@WebMvcTest(controllers = MyDataMainController.class)
class MyDataMainControllerTest extends TestConfig {
	@MockBean
	MyDataMainService myDataMainService;

	@Test
	@DisplayName("/api/v1/main 로 MyData 를 전달한다")
	void getMyDataMain() throws Exception {
		// given
		MyDataMainDto myDataMainDto = new MyDataMainDto(
			new MyDataMainInfoDto(
				new ArrayList<MyDataMainMedicines>(
					Arrays.asList(new MyDataMainMedicines("소화성궤양용제", new ArrayList<MyDataMainMedicine>(
							Arrays.asList(
								new MyDataMainMedicine(1L, "약이름1", 3, 20220901),
								new MyDataMainMedicine(2L, "약이름2", 2, 20220901),
								new MyDataMainMedicine(3L, "약이름3", 1, 20220827)
							))),
						new MyDataMainMedicines("해열, 진통, 소염제", new ArrayList<MyDataMainMedicine>(
							Arrays.asList(
								new MyDataMainMedicine(4L, "약이름1", 3, 20220901),
								new MyDataMainMedicine(5L, "약이름5", 2, 20220901),
								new MyDataMainMedicine(6L, "약이름6", 1, 20220827)
							)))
					)
				)),
			new MyDataDuplicationDto(
				new ArrayList<MyDataDuplicationCase>(
					Arrays.asList(
						new MyDataDuplicationCase(1L, "약이름1",
							new ArrayList<MyDataDuplicationPrescriptionDto>(
								Arrays.asList(
									new MyDataDuplicationPrescriptionDto(20220901, "한길병원", 3, 2, 1),
									new MyDataDuplicationPrescriptionDto(20220901, "다른동네 병원", 3, 2, 1)
								)
							)
						)
					)
				)
			)
		);

		given(myDataMainService.findMedicinesInUse(any())).willReturn(myDataMainDto);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/main")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataMainDto)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("myDataMainInfoDto").type(OBJECT).description("약물 리스트"),
					fieldWithPath("myDataMainInfoDto.effectField").type(ARRAY).description("효과에 해당하는 리스트"),
					fieldWithPath("myDataMainInfoDto.effectField.[].effect").type(STRING).description("효과"),
					fieldWithPath("myDataMainInfoDto.effectField.[].medicines").type(ARRAY).description("효과에 해당하는 약물리스트"),
					fieldWithPath("myDataMainInfoDto.effectField.[].medicines.[].id").type(NUMBER).description("약물 아이디"),
					fieldWithPath("myDataMainInfoDto.effectField.[].medicines.[].medicineName").type(STRING).description("약물 이름"),
					fieldWithPath("myDataMainInfoDto.effectField.[].medicines.[].remainCount").type(NUMBER).description("약물 남은 복용 횟수"),
					fieldWithPath("myDataMainInfoDto.effectField.[].medicines.[].treatDate").type(NUMBER).description("약물 처방 날짜"),
					fieldWithPath("myDataDuplicationDto").type(OBJECT).description("중복약물 리스트"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase").type(ARRAY).description("중복약물 단건"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].medicineId").type(NUMBER).description("중복약물 아이디"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].medicineName").type(STRING).description("중복약물 이름"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto").type(ARRAY).description("중복약물 처방전 건수"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto.[].treatDate").type(NUMBER).description("해당 처방전 처방날짜"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto.[].treatMedicalName").type(STRING).description("해당 병원 이름"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto.[].administerInterval").type(NUMBER).description("투약기간"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto.[].dailyCount").type(NUMBER).description("하루 복용 횟수"),
					fieldWithPath("myDataDuplicationDto.myDataDuplicationCase.[].myDateDuplicationMedicineDto.[].totalDayCount").type(NUMBER).description("하루기준 총 투약 횟수")

				)));
	}
}