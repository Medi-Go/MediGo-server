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

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.global.error.exception.MemberException;
import com.capstone.medigo.global.util.AESProperties;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataLoadService {
	private final AESProperties aesProperties;
	private final MemberRepository memberRepository;
	private final AES256Service aes256Service;

	public Map<String, Object> connectToMyData(Long memberId) {
		Member member = memberRepository.findMemberById(memberId)
			.orElseThrow(() -> MemberException.notFoundMember(memberId));
		String encryptJumin = aes256Service.encrypt(member.getJumin());

		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/common/nhis/TreatmentDosageInfoSimple";
		String token = aesProperties.getToken();
		Map<String, Object> responseInfo = new HashMap<>();
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			setRequestMethodAndProperty(token, conn);

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

		return responseInfo;
	}

	public String getMemberMyData(Long memberId) {
		Member member = memberRepository.findMemberById(memberId)
			.orElseThrow(() -> MemberException.notFoundMember(memberId));

		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/captcha";
		String token = aesProperties.getToken();
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			setRequestMethodAndProperty(token, conn);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("callbackId", member.getCallbackId());
			jsonBody.addProperty("callbackType", member.getCallBackType());
			bw.write(jsonBody.toString());
			bw.flush();

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

	@Transactional
	public void changeMember(Long memberId, Map<String, Object> responseInfo) {
		Member member = memberRepository.findMemberById(memberId)
			.orElseThrow(() -> MemberException.notFoundMember(memberId));
		member.setCallBack(responseInfo);
	}

	private JsonObject addPropertyToJsonBody(Member member, String encryptJumin) {
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("LOGINOPTION", "0");
		jsonBody.addProperty("JUMIN", encryptJumin);
		jsonBody.addProperty("DETAILPARSE", "3");
		jsonBody.addProperty("CHILDPARSE", "1");
		jsonBody.addProperty("USERNAME", member.getName());
		jsonBody.addProperty("HPNUMBER", member.getPhoneNumber());
		jsonBody.addProperty("TELECOMGUBUN", member.getCarrier().getCarrierNumber());
		return jsonBody;
	}

	private Map<String, Object> fillResponseInfo(Map<String, Object> responseInfo, StringBuilder result) {
		JsonObject jsonObject = JsonParser.parseString(result.toString()).getAsJsonObject();
		JsonObject properties = jsonObject.get("data").getAsJsonObject();

		String callbackId = properties.get("callbackId").getAsString();
		String callbackType = properties.get("callbackType").getAsString();
		String callbackData = properties.get("callbackData").getAsString();
		String timeout = properties.get("timeout").getAsString();

		responseInfo.put("errCode", "0001");
		responseInfo.put("callbackId", callbackId);
		responseInfo.put("callBackType", callbackType);
		responseInfo.put("callBackData", callbackData);
		responseInfo.put("timeout", timeout);
		return responseInfo;
	}

	private void setRequestMethodAndProperty(String token, HttpURLConnection conn) throws ProtocolException {
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", token);
		conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		conn.setDoOutput(true);
	}

}