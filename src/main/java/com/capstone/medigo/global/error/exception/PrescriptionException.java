package com.capstone.medigo.global.error.exception;

import java.text.MessageFormat;

import com.capstone.medigo.global.error.dto.ErrorCode;

public class PrescriptionException extends BusinessException {

	protected PrescriptionException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	protected PrescriptionException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public static MemberException invalidEmail(String email) {
		return new MemberException(ErrorCode.INVALID_EMAIL,
			MessageFormat.format("이메일이 유효하지 않습니다. (email: {0})", email));
	}

	public static PrescriptionException notFoundPrescription(Long prescriptionId) {
		return new PrescriptionException(ErrorCode.NOT_FOUND_PRESCRIPTION,
			MessageFormat.format("처방전을 찾을 수 없습니다. (id: {0})", prescriptionId));
	}
}