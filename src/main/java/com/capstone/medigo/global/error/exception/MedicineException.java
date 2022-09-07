package com.capstone.medigo.global.error.exception;

import java.text.MessageFormat;

import com.capstone.medigo.global.error.dto.ErrorCode;

public class MedicineException  extends BusinessException {

	protected MedicineException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	protected MedicineException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public static MemberException notFoundMedicine(Long medicineId) {
		return new MemberException(ErrorCode.NOT_FOUND_MEDICINE,
			MessageFormat.format("약물을 찾을 수 없습니다. (id: {0})", medicineId));
	}
}
