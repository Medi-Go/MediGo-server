package com.capstone.medigo.domain.member.util;

import java.util.regex.Pattern;

import com.capstone.medigo.global.error.exception.MemberException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUtil {
	private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	private static final String PHONE_REGEX = "^01([0|1|6|7|8|9])?([0-9]{7,8})$";

	public static void emailValidation (String email) {
		boolean matches = Pattern.matches(EMAIL_REGEX, email);
		if (!matches) {
			throw MemberException.invalidEmail(email);
		}
	}

	public static void phoneValidation(String phoneNumber) {
		boolean matches = Pattern.matches(PHONE_REGEX, phoneNumber);
		if (!matches) {
			throw MemberException.invalidPhoneNumber(phoneNumber);
		}
	}
}
