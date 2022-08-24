package com.capstone.medigo.domain.member.controller.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;

public record MemberSaveRequest (
	@NotBlank
	String email,
	@NotBlank
	String name,
	@NotBlank
	String phoneNumber,
	@NotBlank
	String jumin,
	@NotBlank
	String carrier
){
	@Builder
	public MemberSaveRequest {
	}
}
