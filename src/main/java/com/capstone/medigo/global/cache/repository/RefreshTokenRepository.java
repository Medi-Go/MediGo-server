package com.capstone.medigo.global.cache.repository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.medigo.global.cache.model.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
