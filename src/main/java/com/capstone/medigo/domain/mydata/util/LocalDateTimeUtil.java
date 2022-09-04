package com.capstone.medigo.domain.mydata.util;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalDateTimeUtil {

	public static int change8format(LocalDateTime time){
		int year = time.getYear();
		int monthValue = time.getMonthValue();
		int dayOfMonth = time.getDayOfMonth();

		StringBuilder sb = new StringBuilder(year);
		sb.append(monthValue<10 ? "0".concat(String.valueOf(monthValue)) : String.valueOf(monthValue));
		sb.append(dayOfMonth<10 ? "0".concat(String.valueOf(monthValue)) : String.valueOf(monthValue));
		return Integer.parseInt(sb.toString());
	}
}
