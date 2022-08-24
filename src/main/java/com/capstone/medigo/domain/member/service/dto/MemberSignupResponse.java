package com.capstone.medigo.domain.member.service.dto;

public record MemberSignupResponse (
	MemberResponse memberResponse,
	String accessToken
){
}
