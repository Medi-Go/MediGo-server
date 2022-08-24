package com.capstone.medigo.domain.mydata.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.mydata.service.loaddata.MyDataService;
import com.capstone.medigo.domain.mydata.util.AES256;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/connect")
@RequiredArgsConstructor
public class MyDataController {
	private final MyDataService myDataService;

	@RequestMapping
	public String connect(HttpSession session) throws Exception {

		String registrationNumber = "971107";
		String encode = AES256.encrypt(registrationNumber);

		HashMap<String, Object> responseInfo = myDataService.connectToMyData(encode, String.valueOf(session.getAttribute("UserId")), String.valueOf(session.getAttribute("access_Token")));
		session.setAttribute("errCode", String.valueOf(responseInfo.get("errCode")));
		session.setAttribute("callbackId", String.valueOf(responseInfo.get("callbackId")));
		session.setAttribute("callBackType", String.valueOf(responseInfo.get("callBackType")));
		session.setAttribute("callBackData", String.valueOf(responseInfo.get("callBackData")));
		session.setAttribute("timeout", String.valueOf(responseInfo.get("timeout")));


		log.info("Connect");
		return "myData/connectionKakao";
	}

	@RequestMapping("/data")
	public String connectionData(HttpSession session){
		String callbackId = (String) session.getAttribute("callbackId");
		String callBackType = (String) session.getAttribute("callBackType");
		myDataService.getData(callbackId,callBackType);
		return "myData/dataSample";
	}
}
