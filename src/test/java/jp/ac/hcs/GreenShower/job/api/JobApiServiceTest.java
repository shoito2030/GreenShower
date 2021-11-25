package jp.ac.hcs.GreenShower.job.api;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobApiServiceTest {
	
	@Autowired
	JobApiService jobApiService;
	
	@Test
	void jsonToMapの正常系テスト() {
		//1.ready
		String json = "{"
				+ "\"classroom\": \"S3A1\", "
				+ "\"class_number\": \"01\""
				+ "}";
		//2.do
		Map<String, Object> result = jobApiService.jsonToMap(json);
		//3.assert
		assertNotNull(result);
		//4.logs
		log.warn("[jsonToMapの正常系テスト]result:"+ result);
	}
	
	@Test
	void jsonToMapの異常系テスト() {
		//1.ready
		String json = "";
		//2.do
		Map<String, Object> result = jobApiService.jsonToMap(json);
		//3.assert
		assertNull(result);
		//4.logs
		log.warn("[jsonToMapの異常系テスト]result:"+ result);
	}

}
