package com.capstone.medigo.domain.member.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.global.cache.model.BlackListToken;
import com.capstone.medigo.global.cache.model.RefreshToken;
import com.capstone.medigo.global.cache.model.TemporaryMember;
import com.capstone.medigo.global.cache.repository.BlackListTokenRepository;
import com.capstone.medigo.global.cache.repository.RefreshTokenRepository;
import com.capstone.medigo.global.cache.repository.TemporaryMemberRepository;
import com.capstone.medigo.global.error.exception.MemberException;
import com.capstone.medigo.global.error.exception.TokenException;
import com.capstone.medigo.global.security.token.Tokens;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final MemberRepository memberRepository;
	private final BlackListTokenRepository blackListTokenRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final TemporaryMemberRepository temporaryMemberRepository;

	// @Transactional
	// public MemberResponse signupMember(MemberDto memberDto) {
	// 	Optional<TemporaryMember> optionalMember = temporaryMemberRepository.findById(memberDto.email());
	// 	if (optionalMember.isEmpty()) {
	// 		throw MemberException.invalidSignup();
	// 	}
	// 	TemporaryMember temporaryMember = optionalMember.get();
	// 	checkDuplicatedEmail(memberDto.email());
	// 	checkDuplicatedNickname(memberDto.nickname());
	// 	Member member = memberRepository.save(MemberConverter.toMember(memberDto, temporaryMember));
	//
	// 	return MemberConverter.toMemberResponse(member);
	// }

	// @Transactional(readOnly = true)
	// public MemberResponse getMemberResponse(Long memberId) {
	// 	Member member = memberRepository.findById(memberId).orElseThrow(() -> {
	// 		throw MemberException.notFoundMember(memberId);
	// 	});
	//
	// 	return MemberConverter.toMemberResponse(member);
	// }

	public Tokens saveRefreshToken(Long memberId, Tokens tokens, long refreshPeriod) {
		Date now = new Date();
		RefreshToken refreshToken = createRefreshToken(memberId, tokens, refreshPeriod, now);
		refreshTokenRepository.save(refreshToken);

		return tokens;
	}

	public void logout(String accessToken, long expiration, String accessTokenWithType) {
		if (accessToken != null) {
			refreshTokenRepository.findById(accessToken)
				.ifPresent((refreshTokenRepository::delete));

			BlackListToken blackListToken = new BlackListToken(accessTokenWithType, expiration);
			blackListTokenRepository.save(blackListToken);
		}
	}

	public String checkAndGetRefreshToken(String accessToken) {
		Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findById(accessToken);
		if(optionalRefreshToken.isEmpty()){
			throw TokenException.refreshTokenExpiration(null);
		}

		return optionalRefreshToken.get().getRefreshTokenValue();
	}

	private RefreshToken createRefreshToken(Long memberId, Tokens tokens, long refreshPeriod, Date now) {
		return RefreshToken.builder()
			.accessTokenValue(tokens.accessToken())
			.memberId(memberId)
			.refreshTokenValue(tokens.refreshToken())
			.createdTime(now)
			.expirationTime(new Date(now.getTime() + refreshPeriod))
			.expiration(refreshPeriod)
			.build();
	}

	private void checkDuplicatedNickname(String nickname) {
		memberRepository.findMemberByNickName(nickname).ifPresent(member -> {
			throw MemberException.nicknameDuplication(nickname);
		});
	}

	private void checkDuplicatedEmail(String email) {
		memberRepository.findMemberByEmail(email).ifPresent(member -> {
			throw MemberException.emailDuplication(email);
		});
	}
}
