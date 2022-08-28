package com.capstone.medigo.domain.mydata.service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.capstone.medigo.global.error.exception.MemberException;

@Service
public class AES256Service {
	@Value("${app.mydata.encspec}")
	private String alg;
	@Value("${app.mydata.enckey}")
	private String key;
	@Value("${app.mydata.enciv}")
	private String iv;
	@Value("${app.mydata.token}")
	private String token;

	public String encrypt(String text) {
		String result;
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
			byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
			result = Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw MemberException.invalidAES();
		}
		return result;
	}

	public String decrypt(String cipherText) {
		String result;
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

			byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = cipher.doFinal(decodedBytes);
			result = new String(decrypted, "UTF-8");
		} catch (Exception e) {
			throw MemberException.invalidAES();
		}
		return result;
	}
}
