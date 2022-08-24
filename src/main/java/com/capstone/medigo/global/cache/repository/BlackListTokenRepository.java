package com.capstone.medigo.global.cache.repository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.medigo.global.cache.model.BlackListToken;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {

}