package com.capstone.medigo.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstone.medigo.domain.member.controller.dto.MemberSaveRequest;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.member.service.dto.MemberResponse;
import com.capstone.medigo.global.cache.model.TemporaryMember;
import com.capstone.medigo.global.cache.repository.TemporaryMemberRepository;
import com.capstone.medigo.global.security.token.Tokens;

@SpringBootTest
class AuthenticationServiceTest {
	@Autowired
	AuthenticationService authenticationService;
	@MockBean
	TemporaryMemberRepository temporaryMemberRepository;

	@Test
	@DisplayName("회원가입을 한다.")
	void testSignupMember() {
		// given
		MemberSaveRequest memberSaveRequest = MemberSaveRequest.builder()
			.email("test1234@test.com")
			.name("홍길동")
			.phoneNumber("01012345678")
			.jumin("19971107")
			.carrier("LG")
			.build();

		TemporaryMember temporaryMember = TemporaryMember.builder()
			.email("test1234@test.com")
			.imageUrl("test.s3.com")
			.nickname("test1234")
			.expiration(500000L)
			.build();

		given(temporaryMemberRepository.findById("test1234@test.com")).willReturn(Optional.of(temporaryMember));
		// when
		MemberResponse memberResponse = authenticationService.signupMember(memberSaveRequest);

		// then
		assertAll(
			() -> assertThat(memberResponse.id()).isNotNull(),
			() -> assertThat(memberResponse.email()).isEqualTo("test1234@test.com"),
			() -> assertThat(memberResponse.profileImageUrl()).isEqualTo("test.s3.com"),
			() -> assertThat(memberResponse.nickName()).isEqualTo("test1234"),
			() -> assertThat(memberResponse.name()).isEqualTo("홍길동"),
			() -> assertThat(memberResponse.jumin()).isEqualTo("19971107"),
			() -> assertThat(memberResponse.carrier()).isEqualTo("LG"),
			() -> assertThat(memberResponse.phoneNumber()).isEqualTo("01012345678"),
			() -> assertThat(memberResponse.lastMyDataLoadUpdateTime().getYear()).isEqualTo(1970),
			() -> assertThat(memberResponse.lastMyDataDetailUpdateTime().getYear()).isEqualTo(1970)
		);
	}

	@Test
	@DisplayName("RefreshToken 을 저장한다.")
	void testSaveRefreshToken() {
		// given
		Long memberId = 1L;
		Tokens tokens = new Tokens("accessToken", "refreshToken");
		long refreshPeriod = 500L;

		// when
		Tokens madeTokens = authenticationService.saveRefreshToken(memberId, tokens, refreshPeriod);

		// then
		assertThat(madeTokens.accessToken()).isEqualTo("accessToken");
		assertThat(madeTokens.refreshToken()).isEqualTo("refreshToken");
	}

}