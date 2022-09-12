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

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.service.MyDataCalendarService;
import com.capstone.medigo.domain.mydata.service.dto.calendar.CalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.MyDataCalendarTreatment;
import com.capstone.medigo.domain.mydata.service.dto.calendar.Treatment;

@WebMvcTest(controllers = MyDataCalendarController.class)
class MyDataCalendarControllerTest extends TestConfig {
	@MockBean
	MyDataCalendarService myDataCalendarService;

	@Test
	@DisplayName("/api/v1/calendar/treatments/{date} 로 캘린더에 필요한 병원 진료 데이터 전달")
	void getCalendarTreatment() throws Exception {
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
		)
		);
	}
}