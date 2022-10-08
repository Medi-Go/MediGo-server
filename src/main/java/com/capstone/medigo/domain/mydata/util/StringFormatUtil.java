package com.capstone.medigo.domain.mydata.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringFormatUtil {
	public static String changeStringBracket(String value) {
		int size = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (ch == '(') {
				size++;
			} else if (ch == ')') {
				size--;
			} else {
				if (size == 0) {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	public static String changePregnantInfo(String value) {
		if (value.length() <= 2) {
			return value;
		}
		String grade = value.substring(0, 3);
		if (grade.equals("1등급")) {
			return "1등급 : 약물 복용 중단 권유";
		} else if (grade.equals("2등급")) {
			return "2등급 : 의사와 상의 필요";
		} else {
			return value;
		}
	}

	public static String changeDURStringBracket(String value) {
		int size = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (ch == '[') {
				size++;
			} else if (ch == ']') {
				size--;
			} else {
				if (size == 0) {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
}
