package com.capstone.medigo.domain.member.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.member.controller.dto.MemberUpdateRequest;
import com.capstone.medigo.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PatchMapping
	public ResponseEntity<Void> member(
		@Valid @RequestBody MemberUpdateRequest memberUpdateRequest,
		@AuthenticationPrincipal Long memberId
	) {
		memberService.updateMember(memberId, memberUpdateRequest);

		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}

	@PostMapping
	public ResponseEntity<Void> updateMemberMyData(
		@AuthenticationPrincipal Long memberId
	){


		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.build();
	}
}
