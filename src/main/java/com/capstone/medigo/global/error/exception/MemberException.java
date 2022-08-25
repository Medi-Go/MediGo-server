package com.capstone.medigo.global.error.exception;

import java.text.MessageFormat;

import com.capstone.medigo.global.error.dto.ErrorCode;

public class MemberException extends BusinessException {

	protected MemberException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	protected MemberException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public static MemberException invalidEmail(String email) {
		return new MemberException(ErrorCode.INVALID_EMAIL,
			MessageFormat.format("이메일이 유효하지 않습니다. (email: {0})", email));
	}

	public static MemberException invalidAES() {
		return new MemberException(ErrorCode.INVALID_AES, "AES 암호화 복호화 과정에서 문제가 발생하였습니다.");
	}

	public static MemberException invalidPhoneNumber(String phoneNumber) {
		return new MemberException(ErrorCode.INVALID_PHONE_NUMBER,
			MessageFormat.format("전화번호 형태가 유효하지 않습니다 (phone number: {0})", phoneNumber));
	}

	public static MemberException invalidMyDataConnection() {
		return new MemberException(ErrorCode.INVALID_MY_DATA_CONNECTION, "이름, 주민번호, 핸드폰 번호, 통신사를 다시 확인하세요.");
	}

	public static MemberException invalidMyDataLoading() {
		return new MemberException(ErrorCode.INVALID_MY_DATA_LOADING, "데이터 허브에서 데이터를 불러오는 과정에 문제가 발생하였습니다.");
	}

	public static MemberException invalidJsonChanging(String errorMessage) {
		return new MemberException(ErrorCode.INVALID_JSON_CHANGING,
			MessageFormat.format("불러온 마이데이터를 json 변환 과정에서 문제가 발생하였습니다.(JsonProcessingException message : {0})",
				errorMessage));
	}

	public static MemberException invalidSignup() {
		return new MemberException(ErrorCode.INVALID_SIGNUP, "회원가입 시간이 지났습니다");
	}

	public static MemberException notFoundMember(Long memberId) {
		return new MemberException(ErrorCode.NOT_FOUND_MEMBER,
			MessageFormat.format("회원을 찾을 수 없습니다. (id: {0})", memberId));
	}

	public static MemberException blacklistDetection() {
		return new MemberException(ErrorCode.BLACKLIST_DETECTION, "이미 logout 된 accessToken 으로의 접근을 감지합니다.");
	}

	public static MemberException emailDuplication(String email) {
		return new MemberException(ErrorCode.REGISTERED_MEMBER,
			MessageFormat.format("이미 등록된 회원 입니다. (email: {0})", email));
	}

	public static MemberException notValidAuthType(String provider) {
		return new MemberException(ErrorCode.INVALID_AUTH_TYPE,
			MessageFormat.format("유효하지 않은 Auth 타입입니다. (provider: {0})", provider));
	}

}

