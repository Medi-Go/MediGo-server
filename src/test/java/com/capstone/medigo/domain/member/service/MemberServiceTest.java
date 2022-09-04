package com.capstone.medigo.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberService memberService;

	String email = "test999@gmail.com";
	Member member;

	@BeforeEach
	@Transactional
	void beforeEach() {
		this.member = memberRepository.save(
			Member.builder()
				.email(email)
				.profileImageUrl("www.s31.com")
				.nickName("test999")
				.name("김철수")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
	}

	@Test
	void testGetMember () {
	    // given // when
		Member foundMember = memberService.getMember(email).orElseThrow(() -> {
			throw new RuntimeException();
		});

		// then
		assertThat(foundMember.getId()).isEqualTo(member.getId());

	}
}