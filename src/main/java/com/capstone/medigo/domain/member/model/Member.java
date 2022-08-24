package com.capstone.medigo.domain.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	@Column(name = "nick_name", length = 30, unique = true, nullable = false)
	private String nickName;

	@Column(name = "profile_image_url", length = 150)
	private String profileImageUrl;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "member_role", length = 16, nullable = false)
	private MemberRole memberRole;

	@Builder
	public Member(Long id, String email, String nickName, String profileImageUrl, MemberRole memberRole) {
		MemberUtil.emailValidation(email);
		this.id = id;
		this.email = email;
		this.nickName = nickName;
		this.profileImageUrl = profileImageUrl;
		this.memberRole = memberRole;
	}
}
