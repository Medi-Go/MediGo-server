package com.capstone.medigo.domain.mydata.repository.kpic;

import com.capstone.medigo.domain.mydata.model.Kpic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpicRepository extends JpaRepository<Kpic, Long>, CustomKpicRepository {

}


