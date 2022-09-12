package com.capstone.medigo.domain.member.service.dto;

import java.time.LocalDateTime;

import lombok.Builder;

public record MemberResponse (
	Long id,
	String email,
	String profileImageUrl,
	String nickName,
	String name,
	String jumin,
	String carrier,
	String phoneNumber,
	LocalDateTime lastMyDataLoadUpdateTime,
	LocalDateTime lastMyDataDetailUpdateTime
){
	@Builder
	public MemberResponse {
	}

}
