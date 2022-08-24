package com.capstone.medigo.global.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@ConfigurationProperties(prefix = "app.mydata")
@Component
@Getter
public class AESProperties {
	private String token;
	private String encspec;
	private String enckey;
	private String enciv;

	public void setToken(String token) {
		this.token = token;
	}

	public void setEncspec(String encspec) {
		this.encspec = encspec;
	}

	public void setEnckey(String enckey) {
		this.enckey = enckey;
	}

	public void setEnciv(String enciv) {
		this.enciv = enciv;
	}
}
