package com.capstone.medigo.domain.mydata.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtil {

	public static int localTo8format(LocalDateTime time) {
		return Integer.parseInt(time.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	}

	public static LocalDateTime eightToLocalFormat(int date) {
		int year = date / 10000;
		int month = (date - year * 10000) / 100;
		int day = (date - year * 10000 - month * 100);
		return LocalDateTime.of(year, month, day, 00, 00, 00);
	}

	public static int getStartDate(Long date) {
		return (int)(date * 100);
	}

	public static int getEndDate(Long date) {
		long year = date / 100;
		long month = date - year * 100;

		if (month == 12) {
			year += 1;
			month = 1;
		} else {
			month += 1;
		}

		return (int)((year * 100 + month) * 100);
	}
}
