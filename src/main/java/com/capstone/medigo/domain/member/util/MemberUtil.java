package com.capstone.medigo.domain.member.util;

import java.util.regex.Pattern;

import com.capstone.medigo.global.error.exception.MemberException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUtil {
	private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

	public static void emailValidation (String email) {
		boolean matches = Pattern.matches(EMAIL_REGEX, email);
		if (!matches) {
			throw MemberException.invalidEmail(email);
		}
	}
}
