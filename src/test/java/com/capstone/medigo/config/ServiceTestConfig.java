package com.capstone.medigo.config;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;

@SpringBootTest
public abstract class ServiceTestConfig {

	@Autowired
	protected MemberRepository memberRepository;

	protected Member member;

	protected Member otherMember;

	@BeforeEach
	@Transactional
	void init() {
		this.member = memberRepository.save(
			Member.builder()
				.email("test1@gmail.com")
				.profileImageUrl("www.s31.com")
				.nickName("test1")
				.name("김철수")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);

		this.otherMember = memberRepository.save(
			Member.builder()
				.email("test2@gmail.com")
				.profileImageUrl("www.s32.com")
				.nickName("test2")
				.name("김영희")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01011113333")
				.build()
		);
	}
}
