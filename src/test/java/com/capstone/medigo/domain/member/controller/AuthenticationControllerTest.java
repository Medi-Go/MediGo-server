package com.capstone.medigo.domain.member.controller;

import static org.mockito.BDDMockito.*;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;

import com.capstone.medigo.config.TestConfig;
import com.capstone.medigo.domain.member.controller.dto.MemberIdRequest;
import com.capstone.medigo.domain.member.controller.dto.MemberSaveRequest;
import com.capstone.medigo.domain.member.model.MemberRole;
import com.capstone.medigo.domain.member.service.AuthenticationService;
import com.capstone.medigo.domain.member.service.dto.MemberResponse;
import com.capstone.medigo.domain.member.service.dto.MemberSignupResponse;
import com.capstone.medigo.global.security.token.TokenService;
import com.capstone.medigo.global.security.token.TokenType;
import com.capstone.medigo.global.security.token.Tokens;

@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest extends TestConfig {
	@MockBean
	TokenService tokenService;

	@MockBean
	AuthenticationService authenticationService;
	@Test
	@DisplayName("/api/v1/signup 에서 회원가입한다")
	void postMemberSignup() throws Exception {
		// given
		MemberSaveRequest memberSaveRequest = MemberSaveRequest.builder()
			.email("test@test.com")
			.name("횽길동")
			.phoneNumber("01012345678")
			.jumin("19971107")
			.carrier("LG")
			.build();

		MemberResponse memberResponse = makeMemberResponse();
		String accessToken = "accessToken";
		MemberSignupResponse memberSignupResponse = new MemberSignupResponse(memberResponse, accessToken);

		Tokens tokens = new Tokens("accessToken", "RefreshToken");

		given(authenticationService.signupMember(memberSaveRequest)).willReturn(memberResponse);
		given(tokenService.generateTokens(memberResponse.id().toString(), MemberRole.ROLE_MEMBER.name()))
			.willReturn(tokens);

		// when
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/signup")
				.content(objectMapper.writeValueAsString(memberSaveRequest))
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().json(objectMapper.writeValueAsString(memberSignupResponse)))
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
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("memberResponse").type(OBJECT).description("멤버"),
					fieldWithPath("memberResponse.id").type(NUMBER).description("아이디"),
					fieldWithPath("memberResponse.email").type(STRING).description("이메일"),
					fieldWithPath("memberResponse.profileImageUrl").type(STRING).description("프로필 이미지 url"),
					fieldWithPath("memberResponse.nickName").type(STRING).description("닉네임"),
					fieldWithPath("memberResponse.name").type(STRING).description("이름"),
					fieldWithPath("memberResponse.jumin").type(STRING).description("주민번호"),
					fieldWithPath("memberResponse.carrier").type(STRING).description("통신사"),
					fieldWithPath("memberResponse.phoneNumber").type(STRING).description("핸드폰 번호"),
					fieldWithPath("accessToken").type(STRING).description("access 토큰값")
				)));
	}

	@Test
	@DisplayName("/api/v1/login 에서 로그인을 한다")
	void postLogin() throws Exception {
		// given
		MemberIdRequest memberIdRequest = new MemberIdRequest(1L);
		MemberResponse memberResponse = makeMemberResponse();
		Tokens tokens = new Tokens("accessToken", "RefreshToken");
		String accessToken = "accessToken";
		MemberSignupResponse memberSignupResponse = new MemberSignupResponse(memberResponse, accessToken);

		given(tokenService.generateTokens(memberIdRequest.id().toString(), MemberRole.ROLE_MEMBER.name())).willReturn(
			tokens);
		given(authenticationService.getMemberResponse(memberIdRequest.id())).willReturn(memberResponse);

		// when
		ResultActions resultActions = mockMvc.perform(
			post("/api/v1/login")
				.content(objectMapper.writeValueAsString(memberIdRequest))
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().json(objectMapper.writeValueAsString(memberSignupResponse)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				requestFields(
					fieldWithPath("id").type(NUMBER).description("아이디")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("memberResponse").type(OBJECT).description("멤버"),
					fieldWithPath("memberResponse.id").type(NUMBER).description("아이디"),
					fieldWithPath("memberResponse.email").type(STRING).description("이메일"),
					fieldWithPath("memberResponse.profileImageUrl").type(STRING).description("프로필 이미지 url"),
					fieldWithPath("memberResponse.nickName").type(STRING).description("닉네임"),
					fieldWithPath("memberResponse.name").type(STRING).description("이름"),
					fieldWithPath("memberResponse.jumin").type(STRING).description("주민번호"),
					fieldWithPath("memberResponse.carrier").type(STRING).description("통신사"),
					fieldWithPath("memberResponse.phoneNumber").type(STRING).description("핸드폰 번호"),
					fieldWithPath("accessToken").type(STRING).description("access 토큰값")
				)));
	}

	@Test
	@DisplayName("/api/v1/logout 에서 로그아웃을 한다")
	void postLogout() throws Exception {
		// given
		String accessToken = "accessToken";
		String accessTokenWithType = "accessTokenWithType";

		given(tokenService.resolveToken(any())).willReturn(accessToken);
		given(tokenService.tokenWithType(accessToken, TokenType.JWT_BLACKLIST)).willReturn(accessTokenWithType);

		// when // then
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/logout")
					.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME));
	}

	@Test
	@DisplayName("/api/v1/auth 에서 프론트는 필요한 유저 정보를 가져온다.")
	void getMemberInfo() throws Exception {
		// given
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		String memberId = "1";
		MemberResponse memberResponse = makeMemberResponse();

		given(tokenService.resolveToken(any())).willReturn(accessToken);
		given(authenticationService.checkAndGetRefreshToken(any())).willReturn(refreshToken);
		given(tokenService.getUid(any())).willReturn(memberId);
		given(authenticationService.getMemberResponse(Long.parseLong(memberId))).willReturn(memberResponse);

		// when
		ResultActions resultActions = mockMvc.perform(
			get("/api/v1/auth")
				.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpectAll(status().isOk(),
				content().json(objectMapper.writeValueAsString(memberResponse)))
			.andDo(print())
			.andDo(document(COMMON_DOCS_NAME,
				requestHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("json 으로 전달")
				),
				responseFields(
					fieldWithPath("id").type(NUMBER).description("아이디"),
					fieldWithPath("email").type(STRING).description("이메일"),
					fieldWithPath("profileImageUrl").type(STRING).description("프로필 이미지 url"),
					fieldWithPath("nickName").type(STRING).description("닉네임"),
					fieldWithPath("name").type(STRING).description("이름"),
					fieldWithPath("jumin").type(STRING).description("주민번호"),
					fieldWithPath("carrier").type(STRING).description("통신사"),
					fieldWithPath("phoneNumber").type(STRING).description("핸드폰 번호")
				)));
	}


	private MemberResponse makeMemberResponse() {
		MemberResponse memberResponse = MemberResponse.builder()
			.id(1L)
			.email("test@test.com")
			.profileImageUrl("s3.com")
			.nickName("치즈볼")
			.name("홍길동")
			.jumin("19971107")
			.carrier("LG")
			.phoneNumber("01012345678")
			.build();
		return memberResponse;
	}
}