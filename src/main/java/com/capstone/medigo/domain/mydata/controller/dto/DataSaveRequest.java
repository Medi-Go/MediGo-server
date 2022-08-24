package com.capstone.medigo.domain.mydata.controller.dto;

import lombok.Builder;

import java.util.ArrayList;

public record DataSaveRequest(
        ArrayList<PrescriptionSaveRequest> MEDICINELIST,
        String ETRACK,
        String ERRMSG,
        String ERRDOC,
        String RESULT
){
    @Builder
    public DataSaveRequest {
    }
}
