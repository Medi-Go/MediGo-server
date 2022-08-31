package com.capstone.medigo.global.security;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.service.MemberService;
import com.capstone.medigo.global.cache.model.TemporaryMember;
import com.capstone.medigo.global.cache.repository.TemporaryMemberRepository;
import com.capstone.medigo.global.util.CoderUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final MemberService memberService;
	private final TemporaryMemberRepository temporaryMemberRepository;

	@Value("${app.oauth.signupTime}")
	private Long signupTime;

	@Value("${app.oauth.domain}")
	private String domain;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email = (String)attributes.get("email");
		Optional<String> optionalName = Optional.ofNullable((String)attributes.get("name"));
		String name = optionalName.orElse("");
		String picture = (String)attributes.get("picture");

		Optional<Member> optionalMember = memberService.getMember(email);

		if (optionalMember.isEmpty()) {
			Optional<TemporaryMember> optionalTemporaryMember = temporaryMemberRepository.findById(email);
			TemporaryMember temporaryMember = makeTemporaryMember(email, name, picture);

			if (optionalTemporaryMember.isEmpty()) {
				temporaryMemberRepository.save(temporaryMember);
			} else {
				temporaryMember = optionalTemporaryMember.get();
			}

			String temporaryMemberEmail = temporaryMember.getEmail();

			String param1 = "?email=" + CoderUtil.encode(temporaryMemberEmail);
			String targetUri = domain + "/signup" + param1;

			response.sendRedirect(targetUri);
			return;
		}
		Long memberId = optionalMember.get().getId();

		String targetUri = domain + "/login/auth" + "?id=" + memberId;
		response.sendRedirect(targetUri);

	}

	private TemporaryMember makeTemporaryMember(String email, String name, String picture) {
		return TemporaryMember.builder()
			.email(email)
			.nickname(name)
			.imageUrl(picture)
			.expiration(signupTime)
			.build();
	}
}
