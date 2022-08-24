package com.capstone.medigo.domain.member.service;

import com.capstone.medigo.domain.member.controller.dto.MemberSaveRequest;
import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.service.dto.MemberResponse;
import com.capstone.medigo.global.cache.model.TemporaryMember;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {
	public static Member toMember(MemberSaveRequest memberSaveRequest, TemporaryMember temporaryMember){
		return Member.builder()
			.email(memberSaveRequest.email())
			.profileImageUrl(temporaryMember.getImageUrl())
			.nickName(temporaryMember.getNickname())
			.name(memberSaveRequest.name())
			.jumin(memberSaveRequest.jumin())
			.carrier(Carrier.valueOf(memberSaveRequest.carrier()))
			.phoneNumber(memberSaveRequest.phoneNumber())
			.build();
	}

	public static MemberResponse toMemberResponse(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.email(member.getEmail())
			.profileImageUrl(member.getProfileImageUrl())
			.nickName(member.getNickName())
			.name(member.getNickName())
			.jumin(member.getJumin())
			.carrier(member.getCarrier().name())
			.phoneNumber(member.getPhoneNumber())
			.build();
	}
}
