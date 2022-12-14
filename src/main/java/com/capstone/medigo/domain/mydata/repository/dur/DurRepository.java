package com.capstone.medigo.domain.mydata.repository.dur;

import com.capstone.medigo.domain.mydata.model.Dur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DurRepository extends JpaRepository<Dur, Long>, CustomDurRepository {
}
