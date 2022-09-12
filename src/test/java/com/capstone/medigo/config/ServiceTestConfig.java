package com.capstone.medigo.config;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.model.Medicine;
import com.capstone.medigo.domain.mydata.model.Prescription;
import com.capstone.medigo.domain.mydata.repository.medicine.MedicineRepository;
import com.capstone.medigo.domain.mydata.repository.prescription.PrescriptionRepository;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;

@SpringBootTest
@Transactional
public abstract class ServiceTestConfig {

	@Autowired
	protected MemberRepository memberRepository;
	@Autowired
	protected PrescriptionRepository prescriptionRepository;
	@Autowired
	protected MedicineRepository medicineRepository;

	protected Member member;

	protected Member otherMember;

	protected Prescription prescriptionBefore5day;

	protected Prescription prescriptionBefore4Month;

	@BeforeEach
	@Transactional
	void init() {
		this.member = memberRepository.save(
			Member.builder()
				.email("test1@gmail.com")
				.profileImageUrl("www.s31.com")
				.nickName("test1")
				.name("김철수")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01012345678")
				.build()
		);
		savePrescription(LocalDateTime.now(), this.member, "처방조제");

		this.otherMember = memberRepository.save(
			Member.builder()
				.email("test2@gmail.com")
				.profileImageUrl("www.s32.com")
				.nickName("test2")
				.name("김영희")
				.jumin("19971107")
				.carrier(Carrier.LG)
				.phoneNumber("01011113333")
				.build()
		);
	}

	protected void savePrescription(LocalDateTime now, Member member, String treatType) {
		prescriptionBefore5day = prescriptionRepository.save(makePrescription(member, now.minusDays(5),treatType));

		medicineRepository.save(makeMedicine(prescriptionBefore5day, prescriptionBefore5day.getTreatDate()));

		prescriptionBefore4Month = prescriptionRepository.save(makePrescription(member, now.minusMonths(4),treatType));

		medicineRepository.save(makeMedicine(prescriptionBefore4Month, prescriptionBefore4Month.getTreatDate()));
	}

	private Medicine makeMedicine(Prescription prescription, int date) {
		return Medicine.builder()
			.prescription(prescription)
			.treatType("약국")
			.medicineNm("록사렉스캡슐75mg (LOXALEX CAP)")
			.treatDate(date)
			.medicineEffect("소화성궤양용제")
			.prescribeCnt("0")
			.administerCnt(3)
			.build();
	}

	private Prescription makePrescription(Member member, LocalDateTime time, String treatType) {
		Prescription prescription = Prescription.builder()
			.member(member)
			.treatType(treatType)
			.visitCnt("1")
			.treatDsnm("김철수")
			.treatDate(LocalDateTimeUtil.localTo8format(time))
			.medicineCnt("3")
			.treatdsgb("1")
			.prescribeCnt("1")
			.treatMedicalnm("한가람약국[남동구 남동대로]")
			.build();
		prescription.changeDetail(3,3,15);

		return prescription;
	}
}
