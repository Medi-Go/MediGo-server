package com.capstone.medigo.domain.mydata.repository.prescription;

import java.util.List;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.model.Prescription;

public interface CustomPrescriptionRepository {

	List<Prescription> findPrescriptionAfterTime(int time, Member member);
}
