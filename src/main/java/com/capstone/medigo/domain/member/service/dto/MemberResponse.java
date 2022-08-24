package com.capstone.medigo.domain.member.service.dto;

import lombok.Builder;

public record MemberResponse (
	Long id,
	String email,
	String profileImageUrl,
	String nickName,
	String name,
	String jumin,
	String carrier,
	String phoneNumber
){
	@Builder
	public MemberResponse {
	}

}
