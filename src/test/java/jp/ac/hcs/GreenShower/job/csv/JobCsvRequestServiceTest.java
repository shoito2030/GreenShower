package jp.ac.hcs.GreenShower.job.csv;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobCsvRequestServiceTest {
	@Autowired
	JobCsvRequestService jobCsvRequestService;
	
	@Test
	void JobRequestListCsvOutの正常系テスト() {
		// 1.Ready
		// 2.Do
		jobCsvRequestService.jobRequestListCsvOut();
		// 3.Assert
		// 4.Logs
		log.warn("[reportListCsvOutの正常系テスト]");
	}
	

	@Test
	void getFileの正常系テスト() throws IOException {
		// 1.Ready
				String fileName = "target/request.csv";
				// 2.Do
				byte[] bytes = jobCsvRequestService.getFile(fileName);
				// 3.Assert
				// 4.Logs
				log.warn("[getFileの正常系テスト]");
	}


}
