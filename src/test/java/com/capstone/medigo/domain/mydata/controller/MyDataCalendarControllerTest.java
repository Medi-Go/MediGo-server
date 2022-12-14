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
	@DisplayName("/api/v1/calendar/treatments/{date} ??? ???????????? ????????? ?????? ?????? ????????? ??????")
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
					parameterWithName("date").description("????????? ????????? ?????? (?????? - 202208)")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json ?????? ??????")
				),
				responseFields(
					fieldWithPath("calendarTreatments").type(ARRAY).description("?????? ????????? ??????"),
					fieldWithPath("calendarTreatments.[].date").type(NUMBER).description("?????? ??????"),
					fieldWithPath("calendarTreatments.[].treatments").type(ARRAY).description("?????? ????????? ???????????? ?????? ????????? ??????"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatType").type(STRING).description("?????? ??????"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatPersonName").type(STRING)
						.description("?????? ????????? ??????"),
					fieldWithPath("calendarTreatments.[].treatments.[].treatMedicalName").type(STRING)
						.description("?????? ?????? ??????")
				)));
	}

	@Test
	@DisplayName("/api/v1/calendar/prescriptions/{date} ??? ???????????? ????????? ?????? ????????? ??????")
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
					parameterWithName("date").description("????????? ????????? ?????? (?????? - 202208)")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json ?????? ??????")
				),
				responseFields(
					fieldWithPath("calendarPrescriptions").type(ARRAY).description("?????? ????????? ??????"),
					fieldWithPath("calendarPrescriptions.[].date").type(NUMBER).description("?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions").type(ARRAY).description("????????? ?????? ?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].prescriptionId").type(NUMBER)
						.description("????????? ?????????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatType").type(STRING)
						.description("?????? ?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatName").type(STRING)
						.description("?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatDate").type(NUMBER)
						.description("?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].treatMedicalName").type(STRING)
						.description("?????? ?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails").type(ARRAY)
						.description("?????? ?????? ?????????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineId").type(
						NUMBER).description("?????? ?????????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineName").type(
						STRING).description("?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].medicineEffect").type(
						STRING).description("?????? ??????"),
					fieldWithPath("calendarPrescriptions.[].prescriptions.[].medicineDetails.[].administerCount").type(
						NUMBER).description("?????? ????????????")
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
			new Treatment("????????????", "?????????", "????????????????????????[????????? ????????????]"),
			new Treatment("????????????", "?????????", "???????????????????????????[????????? ????????????]"),
			new Treatment("????????????", "?????????", "???????????????????????????[????????? ??????????????????]")
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
			.treatType("????????????")
			.treatName("?????????")
			.treatDate(20220120)
			.treatMedicalName("???????????????[????????? ????????????]")
			.medicineDetails(medicineList)
			.build();
	}

	private DetailMedicine makeMedicine(Long id) {
		return DetailMedicine.builder()
			.medicineId(id)
			.medicineName("??????????????????75mg (LOXALEX CAP)")
			.medicineEffect("?????????????????????")
			.administerCount(3)
			.build();
	}
}