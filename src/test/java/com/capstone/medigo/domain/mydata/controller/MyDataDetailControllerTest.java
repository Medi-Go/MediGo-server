package com.capstone.medigo.domain.mydata.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailPrescription;
import com.capstone.medigo.domain.mydata.controller.dto.savedetail.DetailRequest;
import com.capstone.medigo.domain.mydata.service.MyDataDetailService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataDetail;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;

@WebMvcTest(controllers = MyDataDetailController.class)
class MyDataDetailControllerTest extends TestConfig {
	@MockBean
	MyDataDetailService myDataDetailService;

	@Test
	@DisplayName("/api/v1/info-input/{month} 로 MyData 투약 정보 입력에 필요한 리스트 dto 전달한다")
	void getMyDataDetail() throws Exception {
		// given
		MyDataDetail myDataDetail = makeMyDataDetail();
		given(myDataDetailService.getMyDataInfo(any(), any(), anyInt())).willReturn(myDataDetail);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/info-input/{month}", 1)
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataDetail)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				pathParameters(
					parameterWithName("month").description("최근 요청할 데이터의 month 기간")
				),
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("prescriptions").type(ARRAY).description("처방전 배열"),
					fieldWithPath("prescriptions.[].prescriptionId").type(NUMBER).description("처방전 아이디"),
					fieldWithPath("prescriptions.[].treatType").type(STRING).description("처방 진료 형태"),
					fieldWithPath("prescriptions.[].treatName").type(STRING).description("환자 이름"),
					fieldWithPath("prescriptions.[].treatDate").type(NUMBER).description("처방 날짜"),
					fieldWithPath("prescriptions.[].treatMedicalName").type(STRING).description("처방 병원 이름"),
					fieldWithPath("prescriptions.[].medicineDetails").type(ARRAY).description("처방 약물 리스트"),
					fieldWithPath("prescriptions.[].medicineDetails.[].medicineId").type(NUMBER)
						.description("약물 아이디"),
					fieldWithPath("prescriptions.[].medicineDetails.[].medicineName").type(STRING)
						.description("약물 이름"),
					fieldWithPath("prescriptions.[].medicineDetails.[].medicineEffect").type(STRING)
						.description("약물 효과"),
					fieldWithPath("prescriptions.[].medicineDetails.[].administerCount").type(NUMBER)
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
					fieldWithPath("prescriptions").type(ARRAY).description("투약 일수가 적힌 처방전 배열"),
					fieldWithPath("prescriptions.[].prescriptionId").type(NUMBER).description("처방전 아이디"),
					fieldWithPath("prescriptions.[].administerInterval").type(NUMBER).description("투약기간"),
					fieldWithPath("prescriptions.[].dailyCount").type(NUMBER).description("하루 투약 횟수"),
					fieldWithPath("prescriptions.[].totalDayCount").type(NUMBER).description("총 투약 횟수(1일 기준)")
				)));
	}

	private MyDataDetail makeMyDataDetail() {
		List<DetailMedicine> medicineList1 = new ArrayList<>(
			Arrays.asList(makeMedicine(1L), makeMedicine(2L))
		);
		List<DetailMedicine> medicineList2 = new ArrayList<>(
			Arrays.asList(makeMedicine(3L), makeMedicine(4L))
		);
		List<DetailPrescriptionCase> prescriptionList = new ArrayList<>(
			Arrays.asList(makePrescription(1L, medicineList1), makePrescription(2L, medicineList2))
		);
		MyDataDetail myDataDetail = new MyDataDetail(prescriptionList);

		return myDataDetail;
	}

	private DetailPrescription makeDetailPrescription(Long id) {
		return DetailPrescription.builder()
			.prescriptionId(id)
			.administerInterval(1)
			.dailyCount(1)
			.totalDayCount(7)
			.build();
	}

	private DetailPrescriptionCase makePrescription(Long id, List<DetailMedicine> medicineList) {
		return DetailPrescriptionCase.builder()
			.prescriptionId(id)
			.treatType("처방조제")
			.treatName("이용훈")
			.treatDate(20220120)
			.treatMedicalName("한가람약국[남동구 남동대로]")
			.medicineDetails(medicineList)
			.build();
	}

	private DetailMedicine makeMedicine(Long id) {
		return DetailMedicine.builder()
			.medicineId(id)
			.medicineName("록사렉스캡슐75mg (LOXALEX CAP)")
			.medicineEffect("소화성궤양용제")
			.administerCount(3)
			.build();
	}
}