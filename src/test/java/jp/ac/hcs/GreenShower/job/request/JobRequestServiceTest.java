package jp.ac.hcs.GreenShower.job.request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobRequestServiceTest {
	
	@Autowired
	JobRequestService jobRequestService;
	
	@SpyBean
	JobRequestRepository jobRequestRepository;
	
	@Test
	void selectAllRequestsの正常系テスト() {
		// 1.Ready
		String user_id = "1";
		String role = "学生";
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestメソッドの正常系テスト]reportEntity:" + jobRequestEntity.toString());
	}
	
	@Test
	void selectAllRequestsの異常系テスト() {
		// 1.Ready
		String user_id = "1";
		String role = "学生";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectAllRequests(anyString());
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestメソッドの異常系テスト]reportEntity:" + jobRequestEntity.toString());
	}

	@Test
	void selectOneの正常系テスト() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "1";
		String role = "学生";
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);
		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト]jobRequestData:" + jobRequestData.toString());

	}
	
	@Test
	void selectOneの異常系テスト() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "1";
		String role = "学生";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectOne(anyString());
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);
		// 4.Logs
		log.warn("[selectOneメソッドの異常系テスト]jobRequestData:" + jobRequestData.toString());

	}

	@Test
	void selectPersonalInfoStringの正常系テスト() {
		// 1.Ready
		String user_id = "1";
		// 2.Do
		Optional<UserData> userData = jobRequestService.selectPersonalInfo(user_id);
		// 3.Assert
		assertNotNull(userData);
		// 4.Logs
		log.warn("[selectPersonalInfoStringメソッドの正常系テスト]userData:" + userData.toString());
	}
	
	@Test
	void selectPersonalInfoStringの異常系テスト() {
		// 1.Ready
		String user_id = "1";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectPersonalInfo(anyString());
		// 2.Do
		Optional<UserData> userData = jobRequestService.selectPersonalInfo(user_id);
		// 3.Assert
		assertNotNull(userData);
		// 4.Logs
		log.warn("[selectPersonalInfoStringメソッドの異常系テスト]userData:" + userData.toString());
	}

	@Test
	void selectPersonalInfoStringStringの正常系テスト() {
		// 1.Ready
		String classroom = "S3A1";
		String class_number = "01";
		// 2.Do
		UserData userData = jobRequestService.selectPersonalInfo(classroom, class_number);
		// 3.Assert
		assertNotNull(userData);
		// 4.Logs
		log.warn("[selectPersonalInfoStringStringの正常系テスト]userData:" + userData.toString());
	}
	
	@Test
	void selectPersonalInfoStringStringの異常系テスト() {
		// 1.Ready
		String classroom = "S3A1";
		String class_number = "01";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectPersonalInfo(anyString());
		// 2.Do
		UserData userData = jobRequestService.selectPersonalInfo(classroom, class_number);
		// 3.Assert
		assertNotNull(userData);
		// 4.Logs
		log.warn("[selectPersonalInfoStringStringの異常系テスト]userData:" + userData.toString());
	}

	@Test
	void selectStudentsNumberの正常系テスト() {
		// 1.Ready
		String user_id = "1";
		// 2.Do
		int studentsNumber = jobRequestService.selectStudentsNumber(user_id);
		// 3.Assert
		assertNotNull(studentsNumber);
		// 4.Logs
		log.warn("[selectStudentsNumberの正常系テスト]studentsNumber:" + studentsNumber);
	}
	
	@Test
	void selectStudentsNumberの異常系テスト() {
		// 1.Ready
		String user_id = "1";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectStudentsNumber(anyString());
		// 2.Do
		int studentsNumber = jobRequestService.selectStudentsNumber(user_id);
		// 3.Assert
		assertNotNull(studentsNumber);
		// 4.Logs
		log.warn("[selectStudentsNumberの異常系テスト]studentsNumber:" + studentsNumber);
	}

	@Test
	void hasInsertedの正常系テスト() {
		// 1.Ready
		List<String> way = new ArrayList<>();
		way.add("3");
		
		JobRequestForm form = new JobRequestForm();
		form.setApply_type("1");
		form.setCompany_name("株式会社HCS");
		form.setDate_activity_from("2021-11-20T09:30");
		form.setDate_activity_to("2021-11-21T10:30");
		form.setLoc("本校舎9F");
		form.setContent("1");
		form.setWay(way);
		form.setDate_absence_from(null);
		form.setDate_absence_to(null);
		form.setLeave_early_date(null);
		form.setAttendance_date("2021-11-20T09:00");
		form.setRemark("筆記試験です。");
		
		String applicant_id = "isida@xxx.co.jp";
		String register_user_id = "isida@xxx.co.jp";
		// 2.Do
		boolean result = jobRequestService.hasInserted(form, applicant_id, register_user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasInsertedの正常系テスト]result:" + result);
	}
	
//	@Test
//	void hasInsertedの異常系テスト() {
//		// 1.Ready
//		List<String> way = new ArrayList<>();
//		way.add("3");
//		
//		JobRequestForm form = new JobRequestForm();
//		form.setApply_type("1");
//		form.setCompany_name("株式会社HCS");
//		form.setDate_activity_from("2021-11-20T09:30");
//		form.setDate_activity_to("2021-11-21T10:30");
//		form.setLoc("本校舎9F");
//		form.setContent("1");
//		form.setWay(way);
//		form.setDate_absence_from(null);
//		form.setDate_absence_to(null);
//		form.setLeave_early_date(null);
//		form.setAttendance_date("2021-11-20T09:00");
//		form.setRemark("筆記試験です。");
//		
//		String applicant_id = "isida@xxx.co.jp";
//		String register_user_id = "isida@xxx.co.jp";
//		
//		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).insertOne(anyString());
//		// 2.Do
//		boolean result = jobRequestService.hasInserted(form, applicant_id, register_user_id);
//		// 3.Assert
//		assertEquals(true, result);
//		// 4.Logs
//		log.warn("[hasInsertedの異常系テスト]result:" + result);
//	}

	@Test
	void testHasUpdateJobStatus() {
		fail("まだ実装されていません");
	}

	@Test
	void testHasUpdatedJobContent() {
		fail("まだ実装されていません");
	}

	@Test
	void testStrLocalDateTimeToDate() {
		fail("まだ実装されていません");
	}

	@Test
	void testHasInsertedEvent() {
		fail("まだ実装されていません");
	}

	@Test
	void testSelectJobHuntingStatus() {
		fail("まだ実装されていません");
	}

	@Test
	void testUpdateStatusFixed() {
		fail("まだ実装されていません");
	}

}
