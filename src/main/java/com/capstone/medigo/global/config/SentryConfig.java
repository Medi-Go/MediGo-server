package com.capstone.medigo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.sentry.spring.SentryTaskDecorator;

@Configuration
class SentryConfig {

	@Bean
	public SentryTaskDecorator sentryTaskDecorator() {
		return new SentryTaskDecorator();
	}
}