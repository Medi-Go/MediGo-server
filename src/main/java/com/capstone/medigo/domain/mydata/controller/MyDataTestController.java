package com.capstone.medigo.domain.mydata.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.service.MyDataSaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class MyDataTestController {
	private final MemberRepository memberRepository;
	private final MyDataSaveService myDataSaveService;


	@GetMapping("/1004")
	public String testData() throws IOException {
		Member save = memberRepository.save(Member.builder()
			.email("test2@gmail.com")
			.profileImageUrl("www.s32.com")
			.nickName("test2")
			.name("김영희")
			.jumin("19971107")
			.carrier(Carrier.LG)
			.phoneNumber("01011113333")
			.build());
		String result = new String(Files.readAllBytes(Paths.get("src/main/resources/testdata/medicineInfo.json")));

		myDataSaveService.save(result, save.getId());


		return "success";
	}
}
