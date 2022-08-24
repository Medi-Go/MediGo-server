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
public class MedicineInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicine_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "drug_making_company")
    private String makingCompany; // 제조/수입사

    @Column(name = "drug_product_name")
    private String productNm; // 제품명

    @Column(name = "drug_medicine_group")
    private String medicineGroup; // 복지부 분류

    @Column(name = "drug_sales_company")
    private String salesCompany; // 판매사

    @Column(name = "drug_pay_info")
    private String payInfo; // 급여정보

    @Column(name = "drug_administer_path")
    private String administerPath; // 투여경로

    @Column(name = "drug_shape")
    private String shape; // 제형

    @Column(name = "drug_single_yn")
    private String singleYn; // 단일/복합

    @Column(name = "drug_special_yn")
    private String specialYn; // 전문/일반

    @Builder
    public MedicineInfo(Medicine medicine, String makingCompany, String productNm, String medicineGroup, String salesCompany, String payInfo, String administerPath, String shape, String singleYn, String specialYn) {
        this.medicine = medicine;
        this.makingCompany = makingCompany;
        this.productNm = productNm;
        this.medicineGroup = medicineGroup;
        this.salesCompany = salesCompany;
        this.payInfo = payInfo;
        this.administerPath = administerPath;
        this.shape = shape;
        this.singleYn = singleYn;
        this.specialYn = specialYn;
    }
}
