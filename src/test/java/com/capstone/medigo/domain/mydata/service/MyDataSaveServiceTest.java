package com.capstone.medigo.domain.mydata.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyDataSaveServiceTest {
	@Autowired
	MyDataSaveService myDataSaveService;

	@Test
	@DisplayName("json 예시 데이터를 통해 테이터 저장이 성공적으로 되는지 확인한다")
	@Disabled
	void testSaveJsonDataToMyData() throws IOException {
	    // given
		String result = new String(Files.readAllBytes(Paths.get("src/test/resources/medicineInfo.json")));
		// when
		myDataSaveService.save(result);

		// then
		System.out.println(result);

	}

}