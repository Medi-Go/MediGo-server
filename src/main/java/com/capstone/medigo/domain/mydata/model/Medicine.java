package com.capstone.medigo.domain.mydata.model;

import com.capstone.medigo.global.base.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Medicine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @Column(name = "detail_treat_type")
    private String treatType; // 진료형태

    @Column(name = "detail_medicine_name")
    private String medicineNm; // 약이름

    @Column(name = "detail_treat_date")
    private int treatDate; // 진료개시일

    @Column(name = "detail_medicine_effect")
    private String medicineEffect; // 처방 약품 효능

    @Column(name = "detail_prescribe_count")
    private String prescribeCnt; //처방횟수

    @Column(name = "detail_administer_count")
    private int administerCnt; // 투약일수

    @Builder

    public Medicine(Prescription prescription, String treatType, String medicineNm, int treatDate, String medicineEffect, String prescribeCnt, int administerCnt) {
        this.prescription = prescription;
        this.treatType = treatType;
        this.medicineNm = medicineNm;
        this.treatDate = treatDate;
        this.medicineEffect = medicineEffect;
        this.prescribeCnt = prescribeCnt;
        this.administerCnt = administerCnt;
    }
}
