package jp.ac.hcs.GreenShower.job.request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
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
		String user_id = "yamada@xxx.co.jp";
		String role = "ROLE_STUDENT";
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestメソッドの正常系テスト]reportEntity:" + jobRequestEntity.toString());
	}
	
	@Test
	void selectAllRequestsの正常系テスト_ユーザが生徒ではない場合() {
		// 1.Ready
		String user_id = "abe@xxx.co.jp";
		String role = "ROLE_TEACHER";
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestメソッドの正常系テスト_ユーザが生徒ではない場合]reportEntity:" + jobRequestEntity.toString());
	}
	
	@Test
	void selectAllRequestsの正常系テスト_存在しないユーザの場合() {
		// 1.Ready
		String user_id = "empty@xxx.co.jp";
		String role = "ROLE_EMPTY";
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestメソッドの正常系テスト_存在しないユーザの場合]reportEntity:" + jobRequestEntity.toString());
	}
	
	@Test
	void selectAllRequestsの異常系テスト() {
		// 1.Ready
		String user_id = "yamada@xxx.co.jp";
		String role = "ROLE_STUDENT";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectAllRequests(any());
		// 2.Do
		Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests(user_id, role);
		// 3.Assert
		assertTrue(jobRequestEntity.isEmpty());
		// 4.Logs
		log.warn("[selectAllRequestメソッドの異常系テスト]reportEntity:" + jobRequestEntity.toString());
	}

	@Test
	void selectOneの正常系テスト_ROLE_STUDENT() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "yamada@xxx.co.jp";
		String role = "ROLE_STUDENT";
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);
		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト_ROLE_STUDENT]jobRequestData:" + jobRequestData.toString());

	}
	
	@Test
	void selectOneの正常系テスト_ROLE_TEACHER() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "abe@xxx.co.jp";
		String role = "ROLE_TEACHER";
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);
		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト_ROLE_TEACHER]jobRequestData:" + jobRequestData.toString());

	}
	
	@Test
	void selectOneの正常系テスト_ROLE_TEACHERではない場合() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "empty@xxx.co.jp";
		String role = "ROLE_EMPTY";
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);
		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト_ROLE_TEACHERではない場合]jobRequestData:" + jobRequestData.toString());

	}
	
	@Test
	void selectOneの正常系テスト_取得したクラスがROLE_STUDENTのクラス情報ではない場合() {
		// 1.Ready
		String apply_id ="3";
		String user_id = "yamada@xxx.co.jp";
		String role = "ROLE_STUDENT";
//		doReturn(new JobRequestData()).when(jobRequestRepository).selectOne(anyString());
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertNotNull(jobRequestData);

		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト_取得したクラスがROLE_STUDENTのクラス情報ではない場合]jobRequestData:" + jobRequestData.toString());

	}
	
	@Test
	void selectOneの正常系テスト_取得したクラスがROLE_TEACHERのクラス情報ではない場合() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "abe@xxx.co.jp";
		String role = "ROLE_TEACHER";
		doReturn(new JobRequestData()).when(jobRequestRepository).selectOne(anyString());
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertTrue(jobRequestData.isEmpty());

		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト_取得したクラスがROLE_TEACHERのクラス情報ではない場合]jobRequestData:" + jobRequestData.toString());

	}
	
	
	@Test
	void selectOneの異常系テスト() {
		// 1.Ready
		String apply_id ="1";
		String user_id = "1";
		String role = "ROLE_STUDENT";
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectOne(anyString());
		// 2.Do
		Optional<JobRequestData> jobRequestData = jobRequestService.selectOne(apply_id, user_id, role);
		// 3.Assert
		assertTrue(jobRequestData.isEmpty());
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
		assertTrue(userData.isEmpty());
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
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectPersonalInfo(anyString(), anyString());
		// 2.Do
		UserData userData = jobRequestService.selectPersonalInfo(classroom, class_number);
		// 3.Assert
		assertNull(userData);
		// 4.Logs
		log.warn("[selectPersonalInfoStringStringの異常系テスト]userData:" + userData);
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
	
	@Test
	void hasInsertedの正常系テスト_内容複数選択() {
		// 1.Ready
		List<String> way = new ArrayList<>();
		way.add("1");
		way.add("2");
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
		log.warn("[hasInsertedの正常系テスト_内容複数選択]result:" + result);
	}
	
	@Test
	void hasInsertedの正常系テスト_失敗() {
		// 1.Ready
		List<String> way = new ArrayList<>();
		way.add("1");
		way.add("2");
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
		
		doReturn(0).when(jobRequestRepository).insertOne(any());

		// 2.Do
		boolean result = jobRequestService.hasInserted(form, applicant_id, register_user_id);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[hasInsertedの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void hasInsertedの異常系テスト() {
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
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).insertOne(any());
		// 2.Do
		boolean result = jobRequestService.hasInserted(form, applicant_id, register_user_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[hasInsertedの異常系テスト]result:" + result);
	}

	@Test
	void hasUpdateJobStatusの正常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		JobRequestForm form = new JobRequestForm();
		form.setStatus("1");
		form.setIndicate("申請に不備があります");
		
		// 2.Do
		boolean result = jobRequestService.hasUpdateJobStatus(apply_id, form);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasUpdateJobStatusの正常系テスト]result:" + result);
	}
	
	@Test
	void hasUpdateJobStatusの正常系テスト_失敗() {
		// 1.Ready
		String apply_id = "1";
		
		JobRequestForm form = new JobRequestForm();
		form.setStatus("1");
		form.setIndicate("申請に不備があります");
		
		doReturn(0).when(jobRequestRepository).updateJobStatus(anyString(), anyString(), anyString());
		
		// 2.Do
		boolean result = jobRequestService.hasUpdateJobStatus(apply_id, form);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[hasUpdateJobStatusの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void hasUpdateJobStatusの異常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		JobRequestForm form = new JobRequestForm();
		form.setStatus("1");
		form.setIndicate("申請に不備があります");
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).updateJobStatus(anyString(),anyString(),anyString());
		
		// 2.Do
		boolean result = jobRequestService.hasUpdateJobStatus(apply_id, form);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[hasUpdateJobStatusの異常系テスト]result:" + result);
	}

	@Test
	void hasUpdatedJobContentの正常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		List<String> way = new ArrayList<>();
		way.add("3");
		
		JobRequestForm form = new JobRequestForm();
		form.setApply_type("0");
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
		form.setRemark("面接試験です。");
		
		// 2.Do
		boolean result = jobRequestService.hasUpdatedJobContent(apply_id, form);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasUpdatedJobContentの正常系テスト]result:" + result);
	}
	
	@Test
	void hasUpdatedJobContentの正常系テスト_失敗() {
		// 1.Ready
		String apply_id = "1";
		
		List<String> way = new ArrayList<>();
		way.add("3");
		
		JobRequestForm form = new JobRequestForm();
		form.setApply_type("0");
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
		form.setRemark("面接試験です。");
		
		doReturn(0).when(jobRequestRepository).updateJobContent(any(), anyString());
		
		// 2.Do
		boolean result = jobRequestService.hasUpdatedJobContent(apply_id, form);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[hasUpdatedJobContentの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void hasUpdatedJobContentの正常系テスト_内容複数選択() {
		// 1.Ready
		String apply_id = "1";
		
		List<String> way = new ArrayList<>();
		way.add("1");
		way.add("2");
		way.add("3");
		
		JobRequestForm form = new JobRequestForm();
		form.setApply_type("0");
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
		form.setRemark("面接試験です。");
		
		// 2.Do
		boolean result = jobRequestService.hasUpdatedJobContent(apply_id, form);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasUpdatedJobContentの正常系テスト_内容複数選択]result:" + result);
	}
	
	@Test
	void hasUpdatedJobContentの異常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		List<String> way = new ArrayList<>();
		way.add("3");
		
		JobRequestForm form = new JobRequestForm();
		form.setApply_type("0");
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
		form.setRemark("面接試験です。");
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).updateJobContent(any(),anyString());
		
		// 2.Do
		boolean result = jobRequestService.hasUpdatedJobContent(apply_id, form);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[hasUpdatedJobContentの異常系テスト]result:" + result);
	}

	@Test
	void strLocalDateTimeToDateの正常系テスト() {
		// 1.Ready
		String date = "2021-11-20T09:30";
		// 2.Do
		Date resultDate = jobRequestService.strLocalDateTimeToDate(date);
		// 3.Assert
		assertNotNull(resultDate);
		// 4.Logs
		log.warn("[strLocalDateTimeToDateの正常系テスト]resultDate:" + date);
	}
	
	@Test
	void strLocalDateTimeToDateの正常系テスト_未入力の場合() {
		// 1.Ready
		String date = "";
		// 2.Do
		Date resultDate = jobRequestService.strLocalDateTimeToDate(date);
		// 3.Assert
		assertNull(resultDate);
		// 4.Logs
		log.warn("[strLocalDateTimeToDateの正常系テスト_未入力の場合]resultDate:" + date);
	}
	
	@Test
	void strLocalDateTimeToDateの異常系テスト() {
		// 1.Ready
		String date = "2021-11-200009:30";
		// 2.Do
		Date resultDate = jobRequestService.strLocalDateTimeToDate(date);
		// 3.Assert
		assertNull(resultDate);
		// 4.Logs
		log.warn("[strLocalDateTimeToDateの異常系テスト]resultDate:" + date);
	}

	@Test
	void hasInsertedEventの正常系テスト() {
		// 1.Ready
		String user_id = "abe@xxx.co.jp";
		EventForm form = new EventForm();
		form.setCompany_name("株式会社SCC");
		form.setDatetime("2021-12-20T09:30");
		form.setLoc("本校舎9F");
		form.setContent("単独説明会");
		form.setBring("筆記用具");
		
		// 2.Do
		boolean result = jobRequestService.hasInsertedEvent(form, user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasInsertedEventの正常系テスト]result:" + result);
	}
	
	@Test
	void hasInsertedEventの正常系テスト_失敗() {
		// 1.Ready
		String user_id = "abe@xxx.co.jp";
		EventForm form = new EventForm();
		form.setCompany_name("株式会社SCC");
		form.setDatetime("2021-12-20T09:30");
		form.setLoc("本校舎9F");
		form.setContent("単独説明会");
		form.setBring("筆記用具");
		
		doReturn(0).when(jobRequestRepository).insertEvent(any(), anyString());
		// 2.Do
		boolean result = jobRequestService.hasInsertedEvent(form, user_id);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[hasInsertedEventの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void hasInsertedEventの異常系テスト() {
		// 1.Ready
		String user_id = "abe@xxx.co.jp";
		EventForm form = new EventForm();
		form.setCompany_name("株式会社SCC");
		form.setDatetime("2021-12-20T09:30");
		form.setLoc("本校舎9F");
		form.setContent("単独説明会");
		form.setBring("筆記用具");
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).insertEvent(any(),anyString());
		
		// 2.Do
		boolean result = jobRequestService.hasInsertedEvent(form, user_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[hasInsertedEventの異常系テスト]result:" + result);
	}

	@Test
	void selectJobHuntingStatusの正常系テスト() {
		// 1.Ready
		String apply_id = "1";
		// 2.Do
		String result = jobRequestService.selectJobHuntingStatus(apply_id);
		// 3.Assert
		assertNotNull(result);
		// 4.Logs
		log.warn("[selectJobHuntingStatusの正常系テスト]result:" + result);
	}
	
	@Test
	void selectJobHuntingStatusの異常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).selectJobHuntingStatus(anyString());
		// 2.Do
		String result = jobRequestService.selectJobHuntingStatus(apply_id);
		// 3.Assert
		assertNull(result);
		// 4.Logs
		log.warn("[selectJobHuntingStatusの異常系テスト]result:" + result);
	}

	@Test
	void updateStatusFixedの正常系テスト() {
		// 1.Ready
		String apply_id = "1";
		// 2.Do
		boolean result = jobRequestService.updateStatusFixed(apply_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[updateStatusFixedの正常系テスト]result:" + result);
	}
	
	@Test
	void updateStatusFixedの正常系テスト_失敗() {
		// 1.Ready
		String apply_id = "1";
		doReturn(0).when(jobRequestRepository).updateStatusOne(anyString());
		// 2.Do
		boolean result = jobRequestService.updateStatusFixed(apply_id);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[updateStatusFixedの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void updateStatusFixedの異常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).updateStatusOne(anyString());
		// 2.Do
		boolean result = jobRequestService.updateStatusFixed(apply_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[updateStatusFixedの異常系テスト]result:" + result);
	}
	
	@Test
	void hasNoticedCourseDirectorの正常系テスト() {
		// 1.Ready
		String apply_id = "1";
		// 2.Do
		boolean result = jobRequestService.hasNoticedCourseDirector(apply_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[hasNoticedCourseDirectorの正常系テスト]result:" + result);
	}
	
	@Test
	void hasNoticedCourseDirectorの正常系テスト_失敗() {
		// 1.Ready
		String apply_id = "1";
		doReturn(0).when(jobRequestRepository).noticeCourseDirector(anyString());
		// 2.Do
		boolean result = jobRequestService.hasNoticedCourseDirector(apply_id);
		// 3.Assert
		assertFalse(result);
		// 4.Logs
		log.warn("[hasNoticedCourseDirectorの正常系テスト_失敗]result:" + result);
	}
	
	@Test
	void hasNoticedCourseDirectorの異常系テスト() {
		// 1.Ready
		String apply_id = "1";
		
		doThrow(new DataAccessResourceFailureException("")).when(jobRequestRepository).noticeCourseDirector(anyString());
		// 2.Do
		boolean result = jobRequestService.hasNoticedCourseDirector(apply_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[hasNoticedCourseDirectorの異常系テスト]result:" + result);
	}

}
