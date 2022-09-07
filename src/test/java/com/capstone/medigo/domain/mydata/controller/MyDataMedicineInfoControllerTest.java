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
import com.capstone.medigo.domain.mydata.service.MyDataMedicineInfoService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoDurDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoIngredientDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoKpicDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoMedicineDto;
import com.capstone.medigo.domain.mydata.service.dto.MyDataInfoMedicineInfoDto;

@WebMvcTest(controllers = MyDataMedicineInfoController.class)
class MyDataMedicineInfoControllerTest extends TestConfig {
	@MockBean
	MyDataMedicineInfoService myDataMedicineInfoService;

	@Test
	@DisplayName("/api/v1/medicine/{medicineId} 로 약물 세부 정보를 읽어온다")
	void getMedicineInfo() throws Exception {
		// given
		MyDataInfoMedicineDto myDataInfoMedicineDto =
			MyDataInfoMedicineDto.builder()
				.medicineId(1L)
				.medicineName("록사렉스캡슐75mg (LOXALEX CAP)")
				.medicineEffect("소화성궤양용제")
				.myDataInfoMedicineInfoDtoList(
					new ArrayList<MyDataInfoMedicineInfoDto>(
						List.of(
							MyDataInfoMedicineInfoDto.builder()
								.makingCompany("팜젠사이언스")
								.productName("록사렉스캡슐75mg LOXALEX CAP")
								.medicineGroup("소화성궤양용제(232)")
								.salesCompany("")
								.payInfo("670100320-272원캡슐(20220207) / 1")
								.administerPath("경구(내용고형)")
								.shape("서방캡슐")
								.singleYN("")
								.specialYN("1")
								.myDataInfoIngredientDtos(
									new ArrayList<MyDataInfoIngredientDto>(
										List.of(new MyDataInfoIngredientDto(
											"록사티딘아세테이트염산염(Roxatidine Acetate Hydrochloride)mg"))
									)
								)
								.myDataInfoKpicDtos(
									new ArrayList<MyDataInfoKpicDto>(
										List.of(
											new MyDataInfoKpicDto("아세트아미노펜(Acetaminophen) : 통증 질환< 비마약성 진통제<중추성 진통제"))

									)
								)
								.myDataInfoDurDtos(
									new ArrayList<MyDataInfoDurDto>(
										List.of(new MyDataInfoDurDto("[연령금기]12세미만", "[임부금기]없음", "[병용금기]없음"))
									)
								).build()
						)
					)
				)
				.build();

		given(myDataMedicineInfoService.findMedicineInfo(any())).willReturn(myDataInfoMedicineDto);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/medicine/{medicineId}", 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataInfoMedicineDto)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("medicineId").type(NUMBER).description("약물 아이디"),
					fieldWithPath("medicineName").type(STRING).description("약물 이름"),
					fieldWithPath("medicineEffect").type(STRING).description("약물 효과"),
					fieldWithPath("myDataInfoMedicineInfoDtoList").type(ARRAY).description("약물 상세정보 리스트"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].makingCompany").type(STRING).description("제조회사"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].productName").type(STRING).description("상품이름"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].medicineGroup").type(STRING).description("약물그룹"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].salesCompany").type(STRING).description("판매회사"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].payInfo").type(STRING).description("지불정보"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].administerPath").type(STRING)
						.description("관리 경로"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].shape").type(STRING).description("형상"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].singleYN").type(STRING).description("singleYN"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].specialYN").type(STRING).description("specialYN"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoIngredientDtos").type(ARRAY)
						.description("약물 성분 리스트"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoIngredientDtos.[].ingredientName").type(
						STRING).description("성분 이름"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoKpicDtos").type(ARRAY)
						.description("약물 KPIC 리스트"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoKpicDtos.[].kpic").type(STRING)
						.description("KPIC"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoDurDtos").type(ARRAY)
						.description("약물 DUR 리스트"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoDurDtos.[].ageTaboo").type(STRING)
						.description("연렴 금기"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoDurDtos.[].pregnantTaboo").type(STRING)
						.description("임부 금기"),
					fieldWithPath("myDataInfoMedicineInfoDtoList.[].myDataInfoDurDtos.[].combinedTaboo").type(STRING)
						.description("병용 금기")
				)));
	}
}


