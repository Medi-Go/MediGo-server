package com.capstone.medigo.domain.mydata.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.mydata.service.MyDataLoadService;
import com.capstone.medigo.domain.mydata.service.MyDataSaveService;

@WebMvcTest(controllers = MyDataSaveController.class)
class MyDataSaveControllerTest extends TestConfig {
	@MockBean
	MyDataLoadService myDataLoadService;

	@MockBean
	MyDataSaveService myDataSaveService;

	@Test
	@DisplayName("/api/v1/connection 마이데이터 허브를 통해 카카오톡 인증을 받는다")
	void postConnect() throws Exception {
		// given // when // then
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/connection")
				.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME));
	}

	@Test
	@DisplayName("/api/v1/connection/data 에서 마이데이터를 불러온 후 저장한다")
	void postLoadMyData() throws Exception {
		// given
		given(myDataLoadService.getMemberMyData(any())).willReturn(any());

		// when // then
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/connection/data")
				.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME));
	}
}