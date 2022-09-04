package com.capstone.medigo.domain.mydata.controller.dto.save;

import lombok.Builder;

import java.util.ArrayList;
public record MedicineSaveRequest(
        String TREATTYPE,
        String MEDICINENM,
        int TREATDATE,
        String MEDICINEEFFECT,
        String PRESCRIBECNT,
        int ADMINISTERCNT,
        ArrayList<MedicineInfoSaveRequest> DRUGINFOLIST
){
    @Builder
    public MedicineSaveRequest {
    }
}
