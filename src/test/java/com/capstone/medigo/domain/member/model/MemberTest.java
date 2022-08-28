package com.capstone.medigo.domain.member.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.capstone.medigo.domain.member.util.MemberUtil;
import com.capstone.medigo.global.error.exception.MemberException;

class MemberTest {

	@ParameterizedTest
	@DisplayName("이메일 형식에 맞지않으면 예외가 발생한다")
	@ValueSource(strings = {"user@gmail", "usergmail", "@gmail.com", "user@.com"})
	void testEmailValidation(String email) {
		assertThatThrownBy(() -> MemberUtil.emailValidation(email))
			.isInstanceOf(MemberException.class);
	}

	@ParameterizedTest
	@DisplayName("핸드폰 형식에 맞지않으면 예외가 발생한다")
	@ValueSource(strings = {"01012345", "12304985943", "111112312421214125125", "1"})
	void testPhoneValidation(String phoneNumber) {
		assertThatThrownBy(() -> MemberUtil.phoneValidation(phoneNumber))
			.isInstanceOf(MemberException.class);
	}
}