package com.capstone.medigo.domain.mydata.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtil {

	public static int change8format(LocalDateTime time){
		return Integer.parseInt(time.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
	}
}
