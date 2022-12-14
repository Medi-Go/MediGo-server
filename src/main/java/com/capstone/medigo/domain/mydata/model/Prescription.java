package com.capstone.medigo.domain.mydata.model;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.mydata.util.LocalDateTimeUtil;
import com.capstone.medigo.global.base.BaseEntity;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Prescription extends BaseEntity {
	@Id
	@Column(name = "prescription_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(name = "treat_type")
	private String treatType; // 진료형태

	@Column(name = "visit_count")
	private String visitCnt; // 방문일수

	@Column(name = "treat_dsnm")
	private String treatDsnm; // 진료대상자명

	@Column(name = "treat_date")
	private int treatDate; // 진료개시일

	@Column(name = "medicine_count")
	private String medicineCnt; // 투약(요양)횟수

	@Column(name = "treat_dsgb")
	private String treatdsgb; // 1:본인, 2:자녀

	@Column(name = "prescribe_cnt")
	private String prescribeCnt; // 처방 횟수

	@Column(name = "treat_medicalnm")
	private String treatMedicalnm; //병의원(약국)명

	@Column(name = "administer_interval")
	private int administerInterval; // 투약기간(3일에 1번 / 1일에 1번)

	@Column(name = "daily_count")
	private int dailyCount; // 하루 복용 횟수

	@Column(name = "total_day_count")
	private int totalDayCount; // 하루기준 총 투약 횟수

	@Column(name = "end_date")
	private int endDate; // 복용 종료 일자

	@Column(name = "is_update")
	private boolean isUpdate; // 투약 횟수 입력이 되었는지 안되었는지 확인

	@Builder
	public Prescription(Long id, Member member, String treatType, String visitCnt, String treatDsnm, int treatDate,
		String medicineCnt, String treatdsgb, String prescribeCnt, String treatMedicalnm) {
		this.id = id;
		this.member = member;
		this.treatType = treatType;
		this.visitCnt = visitCnt;
		this.treatDsnm = treatDsnm;
		this.treatDate = treatDate;
		this.medicineCnt = medicineCnt;
		this.treatdsgb = treatdsgb;
		this.prescribeCnt = prescribeCnt;
		this.treatMedicalnm = treatMedicalnm;
		this.administerInterval = 0;
		this.dailyCount = 0;
		this.totalDayCount = 0;
		this.endDate = 0;
		isUpdate = false;
	}

	public void changeDetail(int administerInterval, int dailyCount, int totalDayCount) {
		this.administerInterval = administerInterval;
		this.dailyCount = dailyCount;
		this.totalDayCount = totalDayCount;
		this.endDate = LocalDateTimeUtil.localTo8format(
			LocalDateTimeUtil.eightToLocalFormat(this.treatDate).plusDays(totalDayCount));
		this.isUpdate = true;
	}
}
