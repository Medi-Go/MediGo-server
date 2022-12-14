package com.capstone.medigo.domain.mydata.controller.dto.save;

import lombok.Builder;

public record MyDataSaveRequest(
        String errCode,
        String errMsg,
        String result,
        DataSaveRequest data
) {
    @Builder
    public MyDataSaveRequest {
    }
}
