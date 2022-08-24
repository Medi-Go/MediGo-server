package com.capstone.medigo.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.capstone.medigo.global.error.dto.ErrorCode;
import com.capstone.medigo.global.error.dto.ErrorResponse;
import com.capstone.medigo.global.error.dto.TokenExpirationResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseMapper {

	public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponse.of(errorCode));
	}

	public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode errorCode, BindingResult bindingResult) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponse.of(errorCode, bindingResult));
	}

	public static ResponseEntity<TokenExpirationResponse> toTokenExpirationResponse(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(TokenExpirationResponse.of(errorCode));
	}
}
