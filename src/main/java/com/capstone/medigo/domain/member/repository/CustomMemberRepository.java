package com.capstone.medigo.domain.member.repository;

import java.util.Optional;

import com.capstone.medigo.domain.member.model.Member;

public interface CustomMemberRepository {

	Optional<Member> findMemberByEmail(String email);

	Optional<Member> findMemberByNickName(String nickname);
}
