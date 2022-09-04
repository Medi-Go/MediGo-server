package com.capstone.medigo.domain.mydata.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;

@SpringBootTest
class MyDataSaveServiceTest {
	@Autowired
	MyDataSaveService myDataSaveService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("json 예시 데이터를 통해 테이터 저장이 성공적으로 되는지 확인한다")
	@Disabled
	void testSaveJsonDataToMyData() throws IOException {
	    // given
		Member member = memberRepository.save(
			Member.builder()
				.email("test@test.com")
				.profileImageUrl("www.s31.com")
				.nickName("test1")
				.name("김철수")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
		String result = new String(Files.readAllBytes(Paths.get("src/test/resources/medicineInfo.json")));
		// when
		myDataSaveService.save(result, member.getId());

		// then
		System.out.println(result);
	}
}