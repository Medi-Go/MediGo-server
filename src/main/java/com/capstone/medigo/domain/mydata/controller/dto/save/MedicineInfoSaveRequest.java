package com.capstone.medigo.domain.mydata.controller.dto.save;

import lombok.Builder;

import java.util.ArrayList;

public record MedicineInfoSaveRequest(
        String MAKINGCOMPANY,
        String PRODUCTNM,
        String MEDICINEGROUP,
        String SALESCOMPANY,
        String PAYINFO,
        String ATC,
        String ADMINISTERPATH,
        String SHAPE,
        String SINGLEYN,
        String SPECIALYN,
        ArrayList<KPISaveRequest> KPICLIST,
        ArrayList<IngredientSaveRequest> INGREDIENTNMLIST,
        ArrayList<DurSaveRequest> DUR

) {
    @Builder
    public MedicineInfoSaveRequest {
    }
}
