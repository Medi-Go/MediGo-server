package com.capstone.medigo.dbtest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {
	private final MemberRepository memberRepository;
	private final RedisTokenRepository redisTokenRepository;

	@GetMapping("/")
	public String hello() {
		Member member = new Member("yong!");
		memberRepository.save(member);
		RedisToken redisToken = new RedisToken("value", 500000l);
		redisTokenRepository.save(redisToken);

		Member findMember = memberRepository.findById(1L).get();
		RedisToken findRedis = redisTokenRepository.findById("value").get();

		return "hello world" + findMember.getName() + findRedis.getTokenValue();
	}
}
