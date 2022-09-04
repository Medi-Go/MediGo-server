package com.capstone.medigo.domain.mydata.controller.dto.save;

import lombok.Builder;

import java.util.ArrayList;
public record PrescriptionSaveRequest(
    String TREATTYPE,
    String VISITCNT,
    String TREATDSNM,
    int TREATDATE,
    String MEDICINECNT,
    String TREATDSGB,
    String PRESCRIBECNT,
    String TREATMEDICALNM,
    ArrayList<MedicineSaveRequest> DETAILLIST
){
    @Builder
    public PrescriptionSaveRequest {
    }
}
