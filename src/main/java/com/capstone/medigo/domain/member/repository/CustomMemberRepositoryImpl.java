package com.capstone.medigo.domain.member.repository;


import static com.capstone.medigo.domain.member.model.QMember.*;

import java.util.Optional;

import com.capstone.medigo.domain.member.model.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Member> findMemberById(Long memberId) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(member)
				.where(member.id.eq(memberId))
				.fetchOne()
		);
	}

	@Override
	public Optional<Member> findMemberByEmail(String email) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(member)
				.where(member.email.eq(email))
				.fetchOne()
		);
	}

	@Override
	public Optional<Member> findMemberByNickName(String nickname) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(member)
				.where(member.nickName.eq(nickname))
				.fetchOne()
		);
	}
}