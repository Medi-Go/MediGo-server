package com.capstone.medigo.domain.mydata.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capstone.medigo.global.security.token.JwtProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AES256 {
	private static String alg;
	private static String key;
	private static String iv;
	private static String token;

	public AES256(AESProperties aesProperties) {
		this.alg = aesProperties.getEncspec();
		this.key = aesProperties.getEnckey();
		this.iv = aesProperties.getEnciv();
		this.token = aesProperties.getToken();
	}

	public static String encrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decrypt(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, "UTF-8");
	}
}
