package com.capstone.medigo.domain.mydata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Dur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dur_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "medicine_info_id")
    private MedicineInfo medicineInfo;

    @Column(name = "age_taboo")
    private String ageTaboo;

    @Column(name = "pregnant_taboo")
    private String pregnantTaboo;

    @Column(name = "combined_taboo")
    private String combinedTaboo;

    @Builder
    public Dur(MedicineInfo medicineInfo, String ageTaboo, String pregnantTaboo, String combinedTaboo) {
        this.medicineInfo = medicineInfo;
        this.ageTaboo = ageTaboo;
        this.pregnantTaboo = pregnantTaboo;
        this.combinedTaboo = combinedTaboo;
    }
}
