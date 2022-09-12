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
import com.capstone.medigo.domain.mydata.service.MyDataCalendarService;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarPrescription;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailMedicine;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.DetailPrescriptionCase;
import com.capstone.medigo.domain.mydata.service.dto.innerdto.Treatment;

@WebMvcTest(controllers = MyDataCalendarController.class)
class MyDataCalendarControllerTest extends TestConfig {
	@MockBean
	MyDataCalendarService myDataCalendarService;

	@Test
	@DisplayName("/api/v1/calendar/treatments/{date} 로 캘린더에 필요한 병원 진료 데이터 전달")
	void getCalendarTreatments() throws Exception {
		// given
		MyDataCalendarTreatment myDataCalendarTreatment = makeMyDataCalendarTreatment();
		given(myDataCalendarService.getCalendarTreatments(any(), any())).willReturn(myDataCalendarTreatment);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/calendar/treatments/{date}", 202208)
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataCalendarTreatment)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				pathParameters(
					parameterWithName("date").description("달력에 필요한 날짜 (예시 - 202208)")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("calendarTreatments").type(ARRAY).description("진료 데이터 배열"),
					fieldWithPath("calendarTreatments.[].date").type(NUMBER).description("진료 날짜"),
					fieldWithPath("calendarTreatments.[].treatments").type(ARRAY).description("진료 날짜에 해당하는 진료 데이터 배열"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatType").type(STRING).description("진료 타입"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatPersonName").type(STRING)
						.description("진료 당사자 이름"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatMedicalName").type(STRING)
						.description("진료 병원 이름")
				)));
	}

	@Test
	@DisplayName("/api/v1/calendar/prescriptions/{date} 로 캘린더에 필요한 처방 데이터 전달")
	void getCalendarPrescriptions() throws Exception {
		// given
		MyDataCalendarPrescription myDataCalendarTreatment = makeMyDataCalendarPrescription();
		given(myDataCalendarService.getCalendarMedicines(any(), any())).willReturn(myDataCalendarTreatment);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/calendar/prescriptions/{date}", 202208)
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(objectMapper.writeValueAsString(myDataCalendarTreatment)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				pathParameters(
					parameterWithName("date").description("달력에 필요한 날짜 (예시 - 202208)")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("calendarPrescriptions").type(ARRAY).description("처방 데이터 배열"),
					fieldWithPath("calendarPrescriptions.[].date").type(NUMBER).description("처방 날짜"),
					fieldWithPath("calendarPrescriptions.[].prescriptions").type(ARRAY).description("처방전 세부 정보 배열"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].prescriptionId").type(NUMBER)
						.description("처방전 아이디"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatType").type(STRING)
						.description("처방 진료 형태"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatName").type(STRING)
						.description("환자 이름"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatDate").type(NUMBER)
						.description("처방 날짜"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatMedicalName").type(STRING)
						.description("처방 병원 이름"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails").type(ARRAY)
						.description("처방 약물 리스트"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineId").type(
						NUMBER).description("약물 아이디"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineName").type(
						STRING).description("약물 이름"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineEffect").type(
						STRING).description("약물 효과"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].administerCount").type(
						NUMBER).description("약물 투약일수")
				)));
	}

	private MyDataCalendarTreatment makeMyDataCalendarTreatment() {
		return new MyDataCalendarTreatment(
			Arrays.asList(
				makeCalendarTreatment(20210912),
				makeCalendarTreatment(20210918),
				makeCalendarTreatment(20210927)
			)
		);

	}

	private CalendarTreatment makeCalendarTreatment(int date) {
		return new CalendarTreatment(date, Arrays.asList(
			new Treatment("일반외래", "홍길동", "바른성모내과의원[남동구 남동대로]"),
			new Treatment("일반외래", "홍길동", "박병도병원내과의원[남동구 간석대로]"),
			new Treatment("일반외래", "홍길동", "김철수성모내과의원[남동구 미추홀구대로]")
		));
	}

	private MyDataCalendarPrescription makeMyDataCalendarPrescription() {
		return new MyDataCalendarPrescription(
			Arrays.asList(
				makeCalendarPrescription(20210912),
				makeCalendarPrescription(20210918),
				makeCalendarPrescription(20210927)
			)
		);
	}

	private CalendarPrescription makeCalendarPrescription(int date) {
		List<DetailMedicine> medicineList1 = new ArrayList<>(
			Arrays.asList(makeMedicine(1L), makeMedicine(2L))
		);
		List<DetailMedicine> medicineList2 = new ArrayList<>(
			Arrays.asList(makeMedicine(3L), makeMedicine(4L))
		);
		return new CalendarPrescription(date, Arrays.asList(
			makePrescription(1L, medicineList1),
			makePrescription(2L, medicineList2))
		);

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