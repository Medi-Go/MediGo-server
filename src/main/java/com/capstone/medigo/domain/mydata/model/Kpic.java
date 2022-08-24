package com.capstone.medigo.domain.mydata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

import com.capstone.medigo.global.base.BaseEntity;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Kpic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kpic_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "medicine_info_id")
    private MedicineInfo medicineInfo;

    @Column(name = "kpic")
    private String kpic;

    public Kpic(MedicineInfo medicineInfo, String kpic) {
        this.medicineInfo = medicineInfo;
        this.kpic = kpic;
    }
}
