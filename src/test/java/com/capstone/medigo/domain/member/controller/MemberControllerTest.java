package com.capstone.medigo.domain.member.controller;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.member.controller.dto.MemberUpdateRequest;
import com.capstone.medigo.domain.member.service.MemberService;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest extends TestConfig {
	@MockBean
	MemberService memberService;

	@Test
	@DisplayName("/api/v1/member 멤버를 수정한다.")
	void patchMember() throws Exception {
		// given
		MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
			.email("test@test.com")
			.name("횽길동")
			.phoneNumber("01012345678")
			.jumin("19971107")
			.carrier("LG")
			.build();

		// when
		ResultActions resultActions = mockMvc.perform(
			patch("/api/v1/member")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberUpdateRequest)));

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
					fieldWithPath("email").type(STRING).description("이메일"),
					fieldWithPath("name").type(STRING).description("이름"),
					fieldWithPath("phoneNumber").type(STRING).description("핸드폰 번호"),
					fieldWithPath("jumin").type(STRING).description("주민번호"),
					fieldWithPath("carrier").type(STRING).description("통신사")
				)));
	}
}