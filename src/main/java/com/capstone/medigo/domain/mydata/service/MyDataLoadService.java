package com.capstone.medigo.domain.mydata.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.global.error.exception.MemberException;
import com.capstone.medigo.global.util.AES256;
import com.capstone.medigo.global.util.AESProperties;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataLoadService {
	private final AESProperties aesProperties;
	private final MemberRepository memberRepository;

	public Map<String, Object> connectToMyData(Long memberId) {
		Member member = memberRepository.findMemberById(memberId)
			.orElseThrow(() -> MemberException.notFoundMember(memberId));
		String encryptJumin = AES256.encrypt(member.getJumin());

		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/common/nhis/TreatmentDosageInfoSimple";
		String Token = aesProperties.getToken();
		Map<String, Object> responseInfo = new HashMap<>();
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			setRequestMethodAndProperty(Token, conn);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JsonObject jsonBody = addPropertyToJsonBody(member, encryptJumin);
			bw.write(jsonBody.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw MemberException.invalidMyDataConnection();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}

			fillResponseInfo(responseInfo, result);

			br.close();
			bw.close();
		} catch (IOException e) {
			throw MemberException.invalidMyDataConnection();
		}
		setResponseInfoToMember(member, responseInfo);

		return responseInfo;
	}

	public String getMemberMyData(Long memberId) {
		Member member = memberRepository.findMemberById(memberId)
			.orElseThrow(() -> MemberException.notFoundMember(memberId));

		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/captcha";
		String Token = aesProperties.getToken();
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			setRequestMethodAndProperty(Token, conn);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("callbackId", member.getCallbackId());
			jsonBody.addProperty("callbackType", member.getCallBackType());
			bw.write(jsonBody.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw MemberException.invalidMyDataLoading();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line);
			}

			br.close();
			bw.close();
		} catch (IOException e) {
			throw MemberException.invalidMyDataLoading();
		}
		return result.toString();
	}

	private JsonObject addPropertyToJsonBody(Member member, String encryptJumin) {
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("LOGINOPTION", "0");
		jsonBody.addProperty("JUMIN", encryptJumin);
		jsonBody.addProperty("DETAILPARSE", "3");
		jsonBody.addProperty("CHILDPARSE", "1");
		jsonBody.addProperty("USERNAME", member.getName());/////
		jsonBody.addProperty("HPNUMBER", member.getPhoneNumber());/////
		jsonBody.addProperty("TELECOMGUBUN", member.getCarrier().getCarrierNumber());
		return jsonBody;
	}

	private void fillResponseInfo(Map<String, Object> responseInfo, StringBuilder result) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result.toString());
		JsonObject properties = element.getAsJsonObject().get("data").getAsJsonObject();

		String callbackId = properties.getAsJsonObject().get("callbackId").getAsString();
		String callbackType = properties.getAsJsonObject().get("callbackType").getAsString();
		String callbackData = properties.getAsJsonObject().get("callbackData").getAsString();
		String timeout = properties.getAsJsonObject().get("timeout").getAsString();

		responseInfo.put("errCode", "0001");
		responseInfo.put("callbackId", callbackId);
		responseInfo.put("callBackType", callbackType);
		responseInfo.put("callBackData", callbackData);
		responseInfo.put("timeout", timeout);
	}

	private void setRequestMethodAndProperty(String Token, HttpURLConnection conn) throws ProtocolException {
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", Token);
		conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		conn.setDoOutput(true);
	}

	private void setResponseInfoToMember(Member member, Map<String, Object> responseInfo) {
		member.setCallBack(
			String.valueOf(responseInfo.get("errCode")),
			String.valueOf(responseInfo.get("callbackId")),
			String.valueOf(responseInfo.get("callBackType")),
			String.valueOf(responseInfo.get("callBackData"))
		);
	}

}