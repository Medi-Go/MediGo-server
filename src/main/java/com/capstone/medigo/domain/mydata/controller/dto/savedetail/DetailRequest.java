package com.capstone.medigo.domain.mydata.controller.dto.savedetail;

import java.util.List;

public record DetailRequest (
	List<DetailPrescription> prescriptions
){
}
