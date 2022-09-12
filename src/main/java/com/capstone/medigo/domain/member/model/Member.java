package com.capstone.medigo.domain.member.model;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.capstone.medigo.domain.member.controller.dto.MemberUpdateRequest;
import com.capstone.medigo.domain.member.util.MemberUtil;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "email", length = 40, unique = true, nullable = false)
	private String email;

	@Column(name = "profile_image_url", length = 150)
	private String profileImageUrl;

	@Column(name = "nick_name", length = 30, nullable = false)
	private String nickName;

	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Column(name = "jumin", length = 8, nullable = false)
	private String jumin;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "carrier", length = 10, nullable = false)
	private Carrier carrier;

	@Column(name = "phone_number", length = 16, nullable = false)
	private String phoneNumber;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "member_role", length = 16, nullable = false)
	private MemberRole memberRole;

	@Column(name = "callback_id")
	private String callbackId;

	@Column(name = "callback_type")
	private String callBackType;

	@Column(name = "callback_data")
	private String callBackData;

	@Column(name = "my_data_update_time")
	private LocalDateTime myDataLoadUpdateTime;

	@Column(name = "my_data_detail_update_time")
	private LocalDateTime myDataDetailUpdateTime;

	@Builder
	public Member(Long id, String email, String profileImageUrl, String nickName, String name, String jumin,
		Carrier carrier, String phoneNumber) {
		MemberUtil.emailValidation(email);
		MemberUtil.phoneValidation(phoneNumber);
		this.id = id;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.nickName = nickName;
		this.name = name;
		this.jumin = jumin;
		this.carrier = carrier;
		this.phoneNumber = phoneNumber;
		this.memberRole = MemberRole.ROLE_MEMBER;
	}

	public void setCallBack(Map<String, Object> responseInfo) {
		this.callbackId = String.valueOf(responseInfo.get("callbackId"));
		this.callBackType = String.valueOf(responseInfo.get("callBackType"));
		this.callBackData = String.valueOf(responseInfo.get("callBackData"));
	}

	public void changeMyDataLoadUpdateTime(LocalDateTime time){
		this.myDataLoadUpdateTime = time;
	}

	public void changeMyDataDetailUpdateTime(LocalDateTime time){
		this.myDataDetailUpdateTime = time;
	}

	public void changeMemberInfo(MemberUpdateRequest memberUpdateRequest) {
		MemberUtil.emailValidation(email);
		MemberUtil.phoneValidation(phoneNumber);
		this.email = memberUpdateRequest.email();
		this.name = memberUpdateRequest.name();
		this.phoneNumber = memberUpdateRequest.phoneNumber();
		this.jumin = memberUpdateRequest.jumin();
		this.carrier = Carrier.valueOf(memberUpdateRequest.carrier());
	}
}
