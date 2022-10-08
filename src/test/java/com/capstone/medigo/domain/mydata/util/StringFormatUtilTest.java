package com.capstone.medigo.domain.mydata.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StringFormatUtilTest {

	@Test
	@DisplayName("() 제거 로직 구현")
	void changeTest () {
	    // given
		String test1 = "test(123(456)789)1234";
		String test2 = "test1234";

	    // when
		String changedString1 = StringFormatUtil.changeStringBracket(test1);
		String changedString2 = StringFormatUtil.changeStringBracket(test2);

		// then
		assertThat(changedString1).isEqualTo("test1234");
		assertThat(changedString2).isEqualTo("test1234");
	}

	@Test
	@DisplayName("임부 금기 체크")
	void changeTest2 () {
		// given
		String test1 = "[임부금기]2등급 : 명확한 임상적 근거 또는 사유가 있는 경우 부득이하게 사용levosulpiride";
		String test2 = "[임부금기]1등급 : 명확한 임상적 근거 또는 사유가 있는 경우 부득이하게 사용levosulpiride";
		String test3 = "[임부금기]없음";
		String test4 = "";

		// when
		String changedString1 = StringFormatUtil.changePregnantInfo(StringFormatUtil.changeDURStringBracket(test1));
		String changedString2 = StringFormatUtil.changePregnantInfo(StringFormatUtil.changeDURStringBracket(test2));
		String changedString3 = StringFormatUtil.changePregnantInfo(StringFormatUtil.changeDURStringBracket(test3));
		String changedString4 = StringFormatUtil.changePregnantInfo(StringFormatUtil.changeDURStringBracket(test4));

		// then
		assertThat(changedString1).isEqualTo("2등급 : 의사와 상의 필요");
		assertThat(changedString2).isEqualTo("1등급 : 약물 복용 중단 권유");
		assertThat(changedString3).isEqualTo("없음");
		assertThat(changedString4).isEqualTo("");
	}
}