package com.capstone.medigo.domain.member.controller.dto;

import javax.validation.constraints.NotBlank;

public record MemberIdRequest (
	@NotBlank
	Long id
){
}
