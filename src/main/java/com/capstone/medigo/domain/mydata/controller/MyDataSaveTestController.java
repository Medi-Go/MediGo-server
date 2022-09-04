package com.capstone.medigo.domain.mydata.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.service.MyDataLoadService;
import com.capstone.medigo.domain.mydata.service.MyDataSaveService;
import com.capstone.medigo.global.error.exception.MemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/connection")
@RequiredArgsConstructor
public class MyDataSaveTestController {
	private final MyDataLoadService myDataLoadService;
	private final MyDataSaveService myDataSaveService;
	private final MemberRepository memberRepository;
	// 테스트 용도 controller //

	@GetMapping("/test0")
	public String test0() {
		Member myId = memberRepository.save(
			Member.builder()
				.email("dldydgns53@naver.com")
				.profileImageUrl("www.s32.com")
				.nickName("test2")
				.name("이용훈")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01093967385")
				.build()
		);

		return String.valueOf(myId.getId()) + myId.getName();
	}


	@GetMapping("/test1")
	public String test1() {
		Map<String, Object> responseInfo = myDataLoadService.connectToMyData(1L);
		myDataLoadService.changeMember(1L, responseInfo);

		Member member = memberRepository.findMemberById(1L)
			.orElseThrow(() -> MemberException.notFoundMember(1L));

		return member.getCallbackId() + member.getCallBackType();
	}

	@GetMapping("/test2")
	public String test2() {
		String memberMyData = myDataLoadService.getMemberMyData(1L);
		myDataSaveService.save(memberMyData, 1L);

		return memberMyData;
	}
}
