package com.capstone.medigo.domain.mydata.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.medigo.domain.member.model.Carrier;
import com.capstone.medigo.domain.member.model.Member;
import com.capstone.medigo.domain.member.repository.MemberRepository;
import com.capstone.medigo.domain.mydata.service.MyDataSaveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class MyDataTestController {
	private final MemberRepository memberRepository;
	private final MyDataSaveService myDataSaveService;


	@GetMapping("/1004")
	public String testData() throws IOException {
		Member save = memberRepository.save(Member.builder()
			.email("dldydgns530@gmail.com")
			.profileImageUrl("www.s32.com")
			.nickName("test2")
			.name("이용훈")
			.jumin("19971107")
			.carrier(Carrier.LG)
			.phoneNumber("01011113333")
			.build());

		String result = "{\n"
			+ "  \"errCode\": \"0000\",\n"
			+ "  \"errMsg\": \"success\",\n"
			+ "  \"result\": \"SUCCESS\",\n"
			+ "  \"data\": {\n"
			+ "    \"MEDICINELIST\": [\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"처방조제\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"3\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"한가람약국[남동구 남동대로]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"록사렉스캡슐75mg (LOXALEX CAP)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"팜젠사이언스\",\n"
			+ "                \"PRODUCTNM\": \"록사렉스캡슐75mg LOXALEX CAP\",\n"
			+ "                \"MEDICINEGROUP\": \"소화성궤양용제(232)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [],\n"
			+ "                \"PAYINFO\": \"670100320-272원캡슐(20220207) / 1\",\n"
			+ "                \"ATC\": \"A02BA06 : ROXATIDINE\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"서방캡슐\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"록사티딘아세테이트염산염(Roxatidine Acetate Hydrochloride)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"소화성궤양용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"3\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"티메롤8시간이알서방정 (Timerol 8 hours ER Tab.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]12세미만\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"서울제약\",\n"
			+ "                \"PRODUCTNM\": \"티메롤8시간이알서방정 Timerol 8 hours ER Tab.\",\n"
			+ "                \"MEDICINEGROUP\": \"해열, 진통, 소염제(114)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"아세트아미노펜(Acetaminophen) : 통증 질환< 비마약성 진통제<중추성 진통제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"652901880-51원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"N02BE01 : PARACETAMOL(ACETAMINOPHEN)\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"서방정\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"2\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"아세트아미노펜(Acetaminophen)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"해열, 진통, 소염제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"3\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"로스파정 (LOSPA TAB.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"팜젠사이언스\",\n"
			+ "                \"PRODUCTNM\": \"로스파정 LOSPA TAB.\",\n"
			+ "                \"MEDICINEGROUP\": \"진경제(124)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"티로프라미드염산염(Tiropramide Hydrochloride) : 소화기계질환< 진경제<기타\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"670100300-135원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"A03AC05 : TIROPRAMIDE\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"티로프라미드염산염(Tiropramide Hydrochloride)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"진경제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"3\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"미피드정 (Mipide Tab.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"팜젠사이언스\",\n"
			+ "                \"PRODUCTNM\": \"미피드정 Mipide Tab.\",\n"
			+ "                \"MEDICINEGROUP\": \"소화성궤양용제(232)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"레바미피드(Rebamipide) : 소화기계질환< 소화성궤양 치료제<점막보호제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"670103320-103원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"A02BX14 : REBAMIPIDE\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"레바미피드(Rebamipide)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"소화성궤양용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"3\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"바른성모내과의원[남동구 남동대로]\",\n"
			+ "        \"DETAILLIST\": []\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"0\",\n"
			+ "        \"TREATMEDICALNM\": \"(한길의료재단)한길안과병원[부평구 부평대로]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"외래\",\n"
			+ "            \"MEDICINENM\": \"트로페린점안액(1회용) (Tropherine Eye Drops(single use))\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"한미약품\",\n"
			+ "                \"PRODUCTNM\": \"트로페린점안액(1회용) Tropherine Eye Drops(single use)\",\n"
			+ "                \"MEDICINEGROUP\": \"안과용제(131)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"트로픽아미드(Tropicamide) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 산동제<항콜린제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 순환기계질환< 저혈압 치료제<저혈압 치료\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 호흡기계질환< 비강 질환 치료제<비충혈 제거제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 산동제<교감신경 흥분제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"643505741-242원mL/관(20220207) / 0.8\",\n"
			+ "                \"ATC\": \"S01FA56 : TROPICAMIDE, COMBINATIONS\",\n"
			+ "                \"ADMINISTERPATH\": \"눈\",\n"
			+ "                \"SHAPE\": \"점안제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"트로픽아미드(Tropicamide)mg/mL\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"페닐레프린염산염(Phenylephrine Hydrochloride)mg/mL\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"안과용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"1\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"치과외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"0\",\n"
			+ "        \"TREATMEDICALNM\": \"지음치과의원[노원구 동일로]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"외래\",\n"
			+ "            \"MEDICINENM\": \"유니온리도카인에피네프린주(1:100,000) (Union Lidocaine Epinephrine Inj. (1:100,000))\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]2등급 : 명확한 임상적 근거 또는 사유가 있는 경우 부득이하게 사용lidocaine HCl, epinephrine bitartrate\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"한국유니온제약\",\n"
			+ "                \"PRODUCTNM\": \"유니온리도카인에피네프린주(1:100,000) Union Lidocaine Epinephrine Inj. (1:100,000)\",\n"
			+ "                \"MEDICINEGROUP\": \"국소마취제(121)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에피네프린타르타르산염(Epinephrine Bitartrate) : 순환기계질환< 기타 순환기계 약물<아드레날린 효능약\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에피네프린타르타르산염(Epinephrine Bitartrate) : 호흡기계질환< 기관지 천식(COPD 포함) 치료제 (흡입)<교감신경성 기관지 확장제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에피네프린타르타르산염(Epinephrine Bitartrate) : 호흡기계질환< 비강 질환 치료제<비충혈 제거제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에피네프린타르타르산염(Epinephrine Bitartrate) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 녹내장 치료제<교감신경 자극제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에피네프린타르타르산염(Epinephrine Bitartrate) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 치과ㆍ구강용 약물<기타\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate) : 순환기계질환< 혈관보호제<치질과 항문열창의 국소용 제제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 기타 안과용 약물<국소 마취제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 점이용 약물<기타\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate) : 피부/피하조직질환< 기타 약물<국소마취 작용\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate) : 기타 약물< 국소마취제<말초 지각신경 차단\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"665506310-356원mL/앰플(20220207) / 1.8\",\n"
			+ "                \"ATC\": \"N01BB52 : LIDOCAINE, COMBINATIONS\",\n"
			+ "                \"ADMINISTERPATH\": \"주사\",\n"
			+ "                \"SHAPE\": \"주사제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"에피네프린타르타르산염(Epinephrine Bitartrate)mg/mL\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"리도카인염산염수화물(Lidocaine Hydrochloride Hydrate)mg/mL\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"국소마취제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"1\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"처방조제\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"살맛나는온누리약국[부평구 부평대로]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"디쿠아스에스점안액3%(1회용)(0.4mL) (Diquas-S Eye Drops 3%(single use)(0.4mL))\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"미쓰비시다나베파마코리아\",\n"
			+ "                \"PRODUCTNM\": \"디쿠아스에스점안액3%(1회용)(0.4mL) Diquas-S Eye Drops 3%(single use)(0.4mL)\",\n"
			+ "                \"MEDICINEGROUP\": \"안과용제(131)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [],\n"
			+ "                \"PAYINFO\": \"653301021-481원mL/관(20220207) / 0.4\",\n"
			+ "                \"ATC\": \"S01XA20 : ARTIFICIAL TEARS AND OTHER INDIFFERENT PREPARATIONS\",\n"
			+ "                \"ADMINISTERPATH\": \"눈\",\n"
			+ "                \"SHAPE\": \"점안제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"디쿠아포솔나트륨(Diquafosol Tetrasodium)mg/mL\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"안과용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"1\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"(한길의료재단)한길안과병원[부평구 부평대로]\",\n"
			+ "        \"DETAILLIST\": []\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"0\",\n"
			+ "        \"TREATMEDICALNM\": \"생이음한방병원[하남시 미사강변대로]\",\n"
			+ "        \"DETAILLIST\": []\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"속편한내과의원[남동구 인하로507번길]\",\n"
			+ "        \"DETAILLIST\": []\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"처방조제\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"5\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"1\",\n"
			+ "        \"TREATMEDICALNM\": \"로데오약국[남동구 인하로507번길]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"에소듀오정20/800mg (Eso Duo Tab. 20/800mg)\",\n"
			+ "            \"TREATDATE\": \"20210615\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]2등급 : 명확한 임상적 근거 또는 사유가 있는 경우 부득이하게 사용esomeprazole magnesium (as esomeprazole) + sodium bicarbonate\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"종근당\",\n"
			+ "                \"PRODUCTNM\": \"에소듀오정20/800mg Eso Duo Tab. 20/800mg\",\n"
			+ "                \"MEDICINEGROUP\": \"소화성궤양용제(232)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"에스오메프라졸마그네슘삼수화물(Esomeprazole Magnesium Trihydrate) : 소화기계질환< 소화성궤양 치료제<수소펌프 저해제 (PPIs)\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"탄산수소나트륨(Sodium Bicarbonate) : 소화기계질환< 소화성궤양 치료제<제산제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"탄산수소나트륨(Sodium Bicarbonate) : 기타 약물< 관류용제<세정액제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"643308040-720원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"A02BC : PROTON PUMP INHIBITORS\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"에스오메프라졸마그네슘삼수화물(Esomeprazole Magnesium Trihydrate)mg\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"탄산수소나트륨(Sodium Bicarbonate)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"소화성궤양용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"5\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"알드린정 (Aldrin Tab.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"일양약품\",\n"
			+ "                \"PRODUCTNM\": \"알드린정 Aldrin Tab.\",\n"
			+ "                \"MEDICINEGROUP\": \"제산제(234)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"알마게이트(Almagate) : 소화기계질환< 소화성궤양 치료제<제산제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"641701090-45원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"A02AD03 : ALMAGATE\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"2\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"알마게이트(Almagate)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"제산제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"5\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"레프정 (Lep Tab.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]2등급 : 명확한 임상적 근거 또는 사유가 있는 경우 부득이하게 사용levosulpiride\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"바이넥스\",\n"
			+ "                \"PRODUCTNM\": \"레프정 Lep Tab.\",\n"
			+ "                \"MEDICINEGROUP\": \"기타의 소화기관용약(239)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"레보설피리드(Levosulpiride) : 소화기계질환< 위장관 운동 조절제<소화관 운동 조절\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"643100170-119원정(20220207) / 1\",\n"
			+ "                \"ATC\": \"A03FA : PROPULSIVES\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"레보설피리드(Levosulpiride)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"기타의 소화기관용약\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"5\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"무코텍트서방정150mg (Mucotect SR Tab. 150mg)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"녹십자\",\n"
			+ "                \"PRODUCTNM\": \"무코텍트서방정150mg Mucotect SR Tab. 150mg\",\n"
			+ "                \"MEDICINEGROUP\": \"소화성궤양용제(232)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [],\n"
			+ "                \"PAYINFO\": \"\",\n"
			+ "                \"ATC\": \"\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"서방정\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"레바미피드(Rebamipide)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"소화성궤양용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"5\"\n"
			+ "          },\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"약국\",\n"
			+ "            \"MEDICINENM\": \"제이부틴정 (J-butine Tab.)\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"태극제약\",\n"
			+ "                \"PRODUCTNM\": \"제이부틴정 J-butine Tab.\",\n"
			+ "                \"MEDICINEGROUP\": \"기타의 소화기관용약(239)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"트리메부틴말레산염(Trimebutine Maleate) : 소화기계질환< 위장관 운동 조절제<항콜린제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"\",\n"
			+ "                \"ATC\": \"A03AA05 : TRIMEBUTINE\",\n"
			+ "                \"ADMINISTERPATH\": \"경구(내용고형)\",\n"
			+ "                \"SHAPE\": \"정제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"2\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"트리메부틴말레산염(Trimebutine Maleate)mg\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"기타의 소화기관용약\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"5\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"TREATTYPE\": \"일반외래\",\n"
			+ "        \"VISITCNT\": \"1\",\n"
			+ "        \"TREATDSNM\": \"이용훈\",\n"
			+ "        \"TREATDATE\": \"20220907\",\n"
			+ "        \"MEDICINECNT\": \"1\",\n"
			+ "        \"TREATDSGB\": \"1\",\n"
			+ "        \"PRESCRIBECNT\": \"0\",\n"
			+ "        \"TREATMEDICALNM\": \"(한길의료재단)한길안과병원[부평구 부평대로]\",\n"
			+ "        \"DETAILLIST\": [\n"
			+ "          {\n"
			+ "            \"TREATTYPE\": \"외래\",\n"
			+ "            \"MEDICINENM\": \"트로페린점안액(1회용) (Tropherine Eye Drops(single use))\",\n"
			+ "            \"TREATDATE\": \"20220907\",\n"
			+ "            \"DRUGINFOLIST\": [\n"
			+ "              {\n"
			+ "                \"DUR\": [\n"
			+ "                  {\n"
			+ "                    \"AGETABOO\": \"[연령금기]없음\",\n"
			+ "                    \"PREGNANTTABOO\": \"[임부금기]없음\",\n"
			+ "                    \"COMBINEDTABOO\": \"[병용금기]없음\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"MAKINGCOMPANY\": \"한미약품\",\n"
			+ "                \"PRODUCTNM\": \"트로페린점안액(1회용) Tropherine Eye Drops(single use)\",\n"
			+ "                \"MEDICINEGROUP\": \"안과용제(131)\",\n"
			+ "                \"SALESCOMPANY\": \"\",\n"
			+ "                \"KPICLIST\": [\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"트로픽아미드(Tropicamide) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 산동제<항콜린제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 순환기계질환< 저혈압 치료제<저혈압 치료\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 호흡기계질환< 비강 질환 치료제<비충혈 제거제\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"KPIC\": \"페닐레프린염산염(Phenylephrine Hydrochloride) : 눈ㆍ귀ㆍ치과ㆍ구강용 약물< 산동제<교감신경 흥분제\"\n"
			+ "                  }\n"
			+ "                ],\n"
			+ "                \"PAYINFO\": \"643505741-242원mL/관(20220207) / 0.8\",\n"
			+ "                \"ATC\": \"S01FA56 : TROPICAMIDE, COMBINATIONS\",\n"
			+ "                \"ADMINISTERPATH\": \"눈\",\n"
			+ "                \"SHAPE\": \"점안제\",\n"
			+ "                \"SINGLEYN\": \"\",\n"
			+ "                \"SPECIALYN\": \"1\",\n"
			+ "                \"INGREDIENTNMLIST\": [\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"트로픽아미드(Tropicamide)mg/mL\"\n"
			+ "                  },\n"
			+ "                  {\n"
			+ "                    \"INGREDIENTNM\": \"페닐레프린염산염(Phenylephrine Hydrochloride)mg/mL\"\n"
			+ "                  }\n"
			+ "                ]\n"
			+ "              }\n"
			+ "            ],\n"
			+ "            \"MEDICINEEFFECT\": \"안과용제\",\n"
			+ "            \"PRESCRIBECNT\": \"0\",\n"
			+ "            \"ADMINISTERCNT\": \"1\"\n"
			+ "          }\n"
			+ "        ]\n"
			+ "      }\n"
			+ "    ],\n"
			+ "    \"ETRACK\": \"\",\n"
			+ "    \"ERRMSG\": \"\",\n"
			+ "    \"ECODE\": \"\",\n"
			+ "    \"ERRDOC\": \"\",\n"
			+ "    \"RESULT\": \"SUCCESS\"\n"
			+ "  }\n"
			+ "}";

		myDataSaveService.save(result, save.getId());

		return "success";
	}
}
