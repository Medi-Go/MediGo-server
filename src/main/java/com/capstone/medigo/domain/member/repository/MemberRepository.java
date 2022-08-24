package com.capstone.medigo.domain.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.medigo.domain.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {

}