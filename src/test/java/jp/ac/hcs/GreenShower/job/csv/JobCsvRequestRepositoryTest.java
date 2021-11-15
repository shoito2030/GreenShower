package jp.ac.hcs.GreenShower.job.csv;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobCsvRequestRepositoryTest {
	
	@Autowired
	JobCsvRequestRepository jobCsvRequestRepository;
	
	@Test
	void reportlistCsvOutの正常系テスト(){
		// 1.Ready
		// 2.Do
		jobCsvRequestRepository.requestListCsvOut();
		// 3.Assert
		// 4.Logs
		log.warn("[requestListCsvOutメソッドの正常系テスト]");
	}


}
