package com.capstone.medigo.domain.mydata.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.medigo.domain.mydata.service.AES256Service;

@SpringBootTest
class AES256ServiceTest {
	@Autowired
	AES256Service aes256Service;

	@Test
	@DisplayName("AES Encoding, Decoding 테스트")
	void aesTest () throws Exception {
		// given
		String plainData = "!Kwic123테스트";
		String realEncData = "Fw0941fTbBJduIoZJcdb6aD4nfGktinBb460db6zCtc=";

		// when
		String encrypt = aes256Service.encrypt(plainData);
		String decrypt = aes256Service.decrypt(encrypt);

		// then
		assertThat(encrypt).isEqualTo(realEncData);
		assertThat(decrypt).isEqualTo(plainData);
	}
}