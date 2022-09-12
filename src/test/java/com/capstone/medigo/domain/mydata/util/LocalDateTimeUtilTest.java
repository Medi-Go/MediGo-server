package com.capstone.medigo.domain.mydata.util;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocalDateTimeUtilTest {
	
	@Test
	void testLocalTo8format () {
	    // given // when
		int changedTime = LocalDateTimeUtil.localTo8format(LocalDateTime.of(2022, 10, 03, 00, 00, 00));

		// then
		assertThat(changedTime).isEqualTo(20221003);
	}
	
	@Test
	void testEightToLocalFormat () {
	    // given // when
		LocalDateTime changedTime = LocalDateTimeUtil.eightToLocalFormat(20221003);

		// then
		assertThat(changedTime).isEqualTo(LocalDateTime.of(2022, 10, 03, 00, 00, 00));
	}

	@Test
	void testGetStartDate () {
	    // given // when
		int startDate = LocalDateTimeUtil.getStartDate(202207L);

		// then
		assertThat(startDate).isEqualTo(20220700);
	}

	@Test
	void testGetEndDate () {
	    // given // when
		int endDateWith12 = LocalDateTimeUtil.getEndDate(202212L);
		int endDateWith1 = LocalDateTimeUtil.getEndDate(202201L);

		// then
		assertThat(endDateWith12).isEqualTo(20230100);
		assertThat(endDateWith1).isEqualTo(20220200);
	}
}
