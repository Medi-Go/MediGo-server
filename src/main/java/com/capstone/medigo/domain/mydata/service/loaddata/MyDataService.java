package com.capstone.medigo.domain.mydata.service.loaddata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.capstone.medigo.global.util.AESProperties;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDataService {
	private final AESProperties aesProperties;

	public HashMap<String, Object> connectToMyData(String encryptJumin, String userEmail, String access_Token) {

		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/common/nhis/TreatmentDosageInfoSimple";
		String Token = aesProperties.getToken();
		HashMap<String, Object> responseInfo = new HashMap<>();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization",Token);
			conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("LOGINOPTION","0");
			jsonBody.addProperty( "JUMIN",encryptJumin);
			jsonBody.addProperty( "DETAILPARSE","3");
			jsonBody.addProperty( "CHILDPARSE","1");
			jsonBody.addProperty( "USERNAME","이용훈");/////
			jsonBody.addProperty( "HPNUMBER","01093967385");/////
			jsonBody.addProperty( "TELECOMGUBUN","3");

			bw.write(jsonBody.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("data").getAsJsonObject();

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseInfo;
	}


	public void getData(String callbackId, String callBackType) {
		String reqURL = "https://datahub-dev.scraping.co.kr/scrap/captcha";
		String Token = aesProperties.getToken();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			//    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization",Token);
			conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
			conn.setDoOutput(true);

			//    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("callbackId",callbackId);
			jsonBody.addProperty( "callbackType",callBackType);

			bw.write(jsonBody.toString());
			bw.flush();

			//    결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			//    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}