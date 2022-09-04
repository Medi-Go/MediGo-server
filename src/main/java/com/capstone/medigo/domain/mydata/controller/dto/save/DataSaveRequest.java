package com.capstone.medigo.domain.mydata.controller.dto.save;

import lombok.Builder;

import java.util.ArrayList;

public record DataSaveRequest(
        ArrayList<PrescriptionSaveRequest> MEDICINELIST,
        String ETRACK,
        String ERRMSG,
        String ECODE,
        String ERRDOC,
        String RESULT
){
    @Builder
    public DataSaveRequest {
    }
}
