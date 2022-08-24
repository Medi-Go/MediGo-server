package com.capstone.medigo.global.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.capstone.medigo.global.error.exception.MemberException;

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

	public static String encrypt(String text) {
		String result;
		try{
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
			byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
			result = Base64.getEncoder().encodeToString(encrypted);
		}catch (Exception e){
			throw MemberException.invalidAES();
		}
		return result;
	}

	public static String decrypt(String cipherText) {
		String result;
		try{
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

			byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = cipher.doFinal(decodedBytes);
			result = new String(decrypted, "UTF-8");
		}catch (Exception e){
			throw MemberException.invalidAES();
		}
		return result;
	}
}
