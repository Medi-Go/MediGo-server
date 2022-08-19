package com.capstone.medigo.dbtest;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Getter;

@Getter
@RedisHash(value = "testToken")
public class RedisToken {
	@Id
	private final String tokenValue;

	@TimeToLive
	private final Long expiration;

	public RedisToken(String tokenValue, Long expiration) {
		this.tokenValue = tokenValue;
		this.expiration = expiration;
	}
}
