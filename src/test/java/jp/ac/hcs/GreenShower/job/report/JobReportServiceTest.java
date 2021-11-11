package jp.ac.hcs.GreenShower.job.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class JobReportServiceTest {
	
	@Autowired
	JobReportRepository jobReportRepository;
//
//	@Test
//	void selectAllReportsの正常系テスト_ROLE_TEACHER() {
//		//1.ready
//		String role = "ROLE_TEACHER";
//		String classroom = "S3A1";
//		//2.do
//		JobReportEntity jobReportEntity = jobReportRepository.selectAllReports(classroom);
//		//3.assert
//		assertNotNull(jobReportEntity);
//		//4.logs
//		log.warn("[selectAllReportsの正常系テスト_ROLE_TEACHER]jobReportEntity:"+jobReportEntity);
//	}
//	
	@Test
	void selectAllReportsの正常系テスト() {
		//1.ready
		Optional<UserData> userData = null;
		//2.do
		//3.assert
		//4.logs
		log.warn("[selectAllReportsの正常系テスト]userData:"+userData);
	}
	@Test
	void selectAllReportsの正常系テスト_ROLE_STUDENT() {
		//1.ready
		Optional<UserData> userData = null;
		String role = "ROLE_STUDENT";
		String classroom = "S3A1";
		//2.do
		JobReportEntity jobReportEntity = jobReportRepository.selectAllReports(classroom);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_ROLE_STUDENT]jobReportEntity:"+jobReportEntity);
	}
	
	@Test
	void selectAllReportsの異常系テスト() throws DataAccessException{
		//1.ready
		String classroom = "S3A1";
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).selectAllReports(classroom);
		//2.do
		JobReportEntity jobReportEntity = jobReportRepository.selectAllReports(classroom);
		//3.assert
		assertNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_ROLE_STUDENT]jobReportEntity:"+jobReportEntity);
	}
}
