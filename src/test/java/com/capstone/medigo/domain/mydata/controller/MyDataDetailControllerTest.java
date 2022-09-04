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
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailMonthRequest;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailPrescription;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailRequest;
import com.capstone.medigo.domain.mydata.service.MyDataDetailService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetailPrescription;

@WebMvcTest(controllers = MyDataDetailController.class)
class MyDataDetailControllerTest extends TestConfig {
	@MockBean
	MyDataDetailService myDataDetailService;

	@Test
	@DisplayName("/api/v1/info-input 로 MyData 투약 정보 입력에 필요한 리스트 dto 전달한다")
	void getMyDataDetail() throws Exception {
		// given
		DetailMonthRequest detailMonthRequest = new DetailMonthRequest(11);
		List<MyDataDetailMedicine> medicineList1 = new ArrayList<>(
			Arrays.asList(makeMedicine(1L), makeMedicine(2L))
		);
		List<MyDataDetailMedicine> medicineList2 = new ArrayList<>(
			Arrays.asList(makeMedicine(3L), makeMedicine(4L))
		);
		List<MyDataDetailPrescription> prescriptionList = new ArrayList<>(
			Arrays.asList(makePrescription(1L, medicineList1), makePrescription(2L, medicineList2))
		);
		MyDataDetailDto myDataDetailDto = new MyDataDetailDto(prescriptionList);

		given(myDataDetailService.getMyDataInfo(any(), any(), anyInt())).willReturn(myDataDetailDto);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/info-input")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(detailMonthRequest)));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataDetailDto)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				requestFields(
					fieldWithPath("month").type(NUMBER).description("최근 몇달까지의 데이터를 받을지 표시")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("prescriptions").type(ARRAY).description("처방전 리스트"),
					fieldWithPath("prescriptions.[].prescriptionId").type(NUMBER).description("처방전 아이디"),
					fieldWithPath("prescriptions.[].treatType").type(STRING).description("처방 타입"),
					fieldWithPath("prescriptions.[].treatName").type(STRING).description("환자 이름"),
					fieldWithPath("prescriptions.[].treatDate").type(NUMBER).description("처방 날짜"),
					fieldWithPath("prescriptions.[].treatMedicalName").type(STRING).description("처방 병원 이름"),
					fieldWithPath("prescriptions.[].medicineDetailList").type(ARRAY).description("처방 약물 리스트"),
					fieldWithPath("prescriptions.[].medicineDetailList.[].medicineId").type(NUMBER)
						.description("약물 아이디"),
					fieldWithPath("prescriptions.[].medicineDetailList.[].medicineName").type(STRING)
						.description("약물 이름"),
					fieldWithPath("prescriptions.[].medicineDetailList.[].medicineEffect").type(STRING)
						.description("약물 효과"),
					fieldWithPath("prescriptions.[].medicineDetailList.[].administerCount").type(NUMBER)
						.description("약물 투약일수")
				)));
	}

	@Test
	@DisplayName("/api/v1/info-input 로 전달된 MyData Detail 정보를 가지고 Prescription 을 수정한다")
	void patchMyDataDetail() throws Exception {
		// given
		DetailRequest detailRequest = new DetailRequest(
			new ArrayList<>(Arrays.asList(
				makeDetailPrescription(1L),
				makeDetailPrescription(2L)
			))
		);

		// when
		ResultActions resultActions = mockMvc.perform(
			patch("/api/v1/info-input")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(detailRequest)));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				requestFields(
					fieldWithPath("prescriptions").type(ARRAY).description("투약 일수가 적힌 처방전 리스트"),
					fieldWithPath("prescriptions.[].id").type(NUMBER).description("처방전 아이디"),
					fieldWithPath("prescriptions.[].administerInterval").type(NUMBER).description("투약기간"),
					fieldWithPath("prescriptions.[].dailyCount").type(NUMBER).description("하루 투약 횟수"),
					fieldWithPath("prescriptions.[].totalDayCount").type(NUMBER).description("총 투약 횟수(1일 기준)")
				)));
	}

	private DetailPrescription makeDetailPrescription(Long id) {
		return DetailPrescription.builder()
			.id(id)
			.administerInterval(1)
			.dailyCount(1)
			.totalDayCount(7)
			.build();
	}

	private MyDataDetailPrescription makePrescription(Long id, List<MyDataDetailMedicine> medicineList) {
		return MyDataDetailPrescription.builder()
			.prescriptionId(id)
			.treatType("처방조제")
			.treatName("이용훈")
			.treatDate(20220120)
			.treatMedicalName("한가람약국[남동구 남동대로]")
			.medicineDetailList(medicineList)
			.build();
	}

	private MyDataDetailMedicine makeMedicine(Long id) {
		return MyDataDetailMedicine.builder()
			.medicineId(id)
			.medicineName("록사렉스캡슐75mg (LOXALEX CAP)")
			.medicineEffect("소화성궤양용제")
			.administerCount(3)
			.build();
	}
}