package com.capstone.medigo.domain.mydata.model;

import com.capstone.medigo.global.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "medicine_info_id")
    private MedicineInfo medicineInfo;

    @Column(name = "ingredient_name")
    private String ingredientName;

    public Ingredient(MedicineInfo medicineInfo, String ingredientName) {
        this.medicineInfo = medicineInfo;
        this.ingredientName = ingredientName;
    }
}
