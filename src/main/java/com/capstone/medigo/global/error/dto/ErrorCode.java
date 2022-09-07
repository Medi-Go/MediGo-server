package com.capstone.medigo.global.error.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Common
	INVALID_METHOD_ARGUMENT("C001", "Invalid method argument", HttpStatus.BAD_REQUEST),
	UNKNOWN_SERVER_ERROR("C002", "Unknown server error", HttpStatus.INTERNAL_SERVER_ERROR),
	METHOD_NOT_ALLOWED("C003", "Http method not allowed", HttpStatus.METHOD_NOT_ALLOWED),

	// Member
	INVALID_EMAIL("M001", "Email is invalid", HttpStatus.BAD_REQUEST),
	INVALID_AES("M002", "AES encrypt and decrypt are invalid", HttpStatus.BAD_REQUEST),
	INVALID_PHONE_NUMBER("M003", "Phone number format is not invalid", HttpStatus.BAD_REQUEST),
	NOT_FOUND_MEMBER("M004", "Not found member", HttpStatus.BAD_REQUEST),
	INVALID_MY_DATA_CONNECTION("M005", "Not match with My data to user info", HttpStatus.BAD_REQUEST),
	INVALID_MY_DATA_LOADING("M006", "There is problem to load member's my data", HttpStatus.BAD_REQUEST),
	INVALID_JSON_CHANGING("M007", "Converting Json is invalid", HttpStatus.BAD_REQUEST),
	INVALID_SIGNUP("M008", "Signup time is over", HttpStatus.BAD_REQUEST),
	BLACKLIST_DETECTION("M009", "AccessToken is deprived", HttpStatus.BAD_REQUEST),
	REGISTERED_MEMBER("M010", "Member is already registered", HttpStatus.BAD_REQUEST),
	INVALID_AUTH_TYPE("M011", "AUTH type is invalid", HttpStatus.BAD_REQUEST),

	// Prescription
	NOT_FOUND_PRESCRIPTION("DP001", "Not found prescription", HttpStatus.BAD_REQUEST),

	// Medicine
	NOT_FOUND_MEDICINE("DM001", "Not found medicine", HttpStatus.BAD_REQUEST),

	// Token Expiration
	ACCESS_TOKEN_EXPIRATION("T001", "Access token is expired", HttpStatus.BAD_REQUEST),
	REFRESH_TOKEN_EXPIRATION("T002", "Refresh token is expired", HttpStatus.BAD_REQUEST);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(String code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
