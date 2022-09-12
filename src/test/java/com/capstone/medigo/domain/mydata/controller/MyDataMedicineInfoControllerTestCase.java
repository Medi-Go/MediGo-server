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
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.service.MyDataMedicineInfoService;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.IngredientInfo;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.MedicineInfoCase;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DurInfo;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.KpicInfo;
import com.capstone.medigo.domain.mydata.service.dto.MyDataMedicineInfo;

@WebMvcTest(controllers = MyDataMedicineInfoController.class)
class MyDataMedicineInfoControllerTestCase extends TestConfig {
	@MockBean
	MyDataMedicineInfoService myDataMedicineInfoService;

	@Test
	@DisplayName("/api/v1/medicine/{medicineId} 로 약물 세부 정보를 읽어온다")
	void getMedicineInfo() throws Exception {
		// given
		MyDataMedicineInfo myDataMedicineInfo = makeMyDataMedicineInfo();
		given(myDataMedicineInfoService.findMedicineInfo(any())).willReturn(myDataMedicineInfo);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/medicine/{medicineId}", 1)
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataMedicineInfo)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				pathParameters(
					parameterWithName("medicineId").description("요청할 약물 아이디")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("medicineId").type(NUMBER).description("약물 아이디"),
					fieldWithPath("medicineName").type(STRING).description("약물 이름"),
					fieldWithPath("medicineEffect").type(STRING).description("약물 효과"),
					fieldWithPath("medicineInfoCases").type(ARRAY).description("약물 상세정보 리스트"),
					fieldWithPath("medicineInfoCases.[].makingCompany").type(STRING).description("제조회사"),
					fieldWithPath("medicineInfoCases.[].productName").type(STRING).description("상품이름"),
					fieldWithPath("medicineInfoCases.[].medicineGroup").type(STRING).description("약물그룹"),
					fieldWithPath("medicineInfoCases.[].salesCompany").type(STRING).description("판매회사"),
					fieldWithPath("medicineInfoCases.[].payInfo").type(STRING).description("지불정보"),
					fieldWithPath("medicineInfoCases.[].administerPath").type(STRING).description("관리 경로"),
					fieldWithPath("medicineInfoCases.[].shape").type(STRING).description("형상"),
					fieldWithPath("medicineInfoCases.[].singleYN").type(STRING).description("singleYN"),
					fieldWithPath("medicineInfoCases.[].specialYN").type(STRING).description("specialYN"),
					fieldWithPath("medicineInfoCases.[].ingredientInfos").type(ARRAY).description("약물 성분 리스트"),
					fieldWithPath("medicineInfoCases.[].ingredientInfos.[].ingredientName").type(STRING)
						.description("성분 이름"),
					fieldWithPath("medicineInfoCases.[].kpicInfos").type(ARRAY).description("약물 KPIC 리스트"),
					fieldWithPath("medicineInfoCases.[].kpicInfos.[].kpic").type(STRING).description("KPIC"),
					fieldWithPath("medicineInfoCases.[].durInfos").type(ARRAY).description("약물 DUR 리스트"),
					fieldWithPath("medicineInfoCases.[].durInfos.[].ageTaboo").type(STRING).description("연렴 금기"),
					fieldWithPath("medicineInfoCases.[].durInfos.[].pregnantTaboo").type(STRING).description("임부 금기"),
					fieldWithPath("medicineInfoCases.[].durInfos.[].combinedTaboo").type(STRING).description("병용 금기")
				)));
	}

	private MyDataMedicineInfo makeMyDataMedicineInfo() {
		MyDataMedicineInfo myDataMedicineInfo =
			MyDataMedicineInfo.builder()
				.medicineId(1L)
				.medicineName("록사렉스캡슐75mg (LOXALEX CAP)")
				.medicineEffect("소화성궤양용제")
				.medicineInfoCases(
					new ArrayList<MedicineInfoCase>(
						List.of(
							MedicineInfoCase.builder()
								.makingCompany("팜젠사이언스")
								.productName("록사렉스캡슐75mg LOXALEX CAP")
								.medicineGroup("소화성궤양용제(232)")
								.salesCompany("")
								.payInfo("670100320-272원캡슐(20220207) / 1")
								.administerPath("경구(내용고형)")
								.shape("서방캡슐")
								.singleYN("")
								.specialYN("1")
								.ingredientInfos(
									new ArrayList<>(
										List.of(new IngredientInfo(
											"록사티딘아세테이트염산염(Roxatidine Acetate Hydrochloride)mg"))
									)
								)
								.kpicInfos(
									new ArrayList<>(
										List.of(
											new KpicInfo("아세트아미노펜(Acetaminophen) : 통증 질환< 비마약성 진통제<중추성 진통제"))

									)
								)
								.durInfos(
									new ArrayList<>(
										List.of(new DurInfo("[연령금기]12세미만", "[임부금기]없음", "[병용금기]없음"))
									)
								).build()
						)
					)
				)
				.build();
		return myDataMedicineInfo;
	}
}


