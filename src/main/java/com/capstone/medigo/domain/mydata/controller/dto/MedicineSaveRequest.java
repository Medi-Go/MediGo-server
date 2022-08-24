package com.capstone.medigo.domain.mydata.controller.dto;

import lombok.Builder;

import java.util.ArrayList;
public record MedicineSaveRequest(
        String TREATTYPE,
        String MEDICINENM,
        String TREATDATE,
        String MEDICINEEFFECT,
        String PRESCRIBECNT,
        String ADMINISTERCNT,
        ArrayList<MedicineInfoSaveRequest> DRUGINFOLIST


){
    @Builder
    public MedicineSaveRequest {
    }
}
