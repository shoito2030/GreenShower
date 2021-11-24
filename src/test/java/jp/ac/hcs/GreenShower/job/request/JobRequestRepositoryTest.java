package jp.ac.hcs.GreenShower.job.request;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Apply_type;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;
import jp.ac.hcs.GreenShower.job.request.JobRequestData.Way;
import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobRequestRepositoryTest {

	@Autowired
	JobRequestRepository jobRequestRepository;

	@SpyBean
	JobRequestRepository repository;
	
	@Mock
	JdbcTemplate jdbc;
	
	@Test
	void selectAllRequestsメソッドの正常系テスト() {
		// 1.Ready
		String classroom = "S3A1";
		// 2.Do
		JobRequestEntity jobRequestEntity = jobRequestRepository.selectAllRequests(classroom);
		// 3.Assert
		assertNotNull(jobRequestEntity);
		// 4.Logs
		log.warn("[selectAllRequestsメソッドの正常系テスト]jobRequestEntity:" + jobRequestEntity.toString());
	}

	@Test
	void selectOneメソッドの正常系テスト() {
		String apply_id = "1";

		JobRequestData jobRequestData = jobRequestRepository.selectOne(apply_id);

		assertNotNull(jobRequestData);

		log.warn("[selectOneメソッドの正常系テスト]jobRequestData:" + jobRequestData.toString());
	}

	@Test
	void selectPersonalInfoメソッドの正常系テスト() {
		String user_id = "isida@xxx.co.jp";

		UserData userData = jobRequestRepository.selectPersonalInfo(user_id);

		assertNotNull(userData);

		log.warn("[selectPersonalInfoメソッドの正常系テスト]userData:" + userData.toString());
	}

	@Test
	void API用のselectPersonalInfoメソッドの正常系テスト() {
		String classroom = "S3A1";
		String class_number = "01";

		UserData userData = jobRequestRepository.selectPersonalInfo(classroom, class_number);

		assertNotNull(userData);

		log.warn("[API用のselectPersonalInfoメソッドの正常系テスト]userData:" + userData.toString());
	}

	@Test
	void selectStudentsNumberメソッドの正常系テスト() {
		String user_id = "isida@xxx.co.jp";

		int studentNumber = jobRequestRepository.selectStudentsNumber(user_id);

		assertNotEquals(studentNumber, 0);

		log.warn("[selectStudentsNumberメソッドの正常系テスト]studentNumber:" + studentNumber);
	}

	@Test
	void insertOneメソッドの正常系テスト() {
		JobRequestData data = new JobRequestData();
		data.setApply_id("0");
		data.setApplicant_id("isida@xxx.co.jp");
		data.setContent(Content.TEST);
		data.setCompany_name("insertOne株式会社");
		data.setApply_type(Apply_type.PERSONAL);
		data.setDate_activity_from(new Date());
		data.setDate_activity_to(new Date());
		data.setLoc("HCS");
		data.setWay(Way.ABSENCE);
		data.setDate_absence_from(new Date());
		data.setDate_absence_to(new Date());
		data.setLeave_early_date(null);
		data.setDate_absence_from(null);
		data.setRemark("insertOneメソッドの正常系テスト");
		data.setRegister_user_id("isida@xxx.co.jp");

		int rowNumber = jobRequestRepository.insertOne(data);

		assertEquals(rowNumber, 1);

		log.warn("[insertOneメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void selectApply_idメソッドの正常系テスト() {
		int apply_id = jobRequestRepository.selectApply_id();

		assertNotNull(apply_id);

		log.warn("[selectApply_idメソッドの正常系テスト]apply_id:" + apply_id);
	}
	
//	@Test
//	void selectApply_idメソッドの正常系テスト_apply_idが取得できなかった時() {
//		//JdbcTemplate jdbc = mock(JdbcTemplate.class);
//		//Map<String, Object> hoge = new Map<String, Object>();
//		
////		doReturn(null).when(jdbc).queryForMap(anyString());
//		doThrow(new  IncorrectResultSizeDataAccessException(0)).when(jdbc).queryForMap(anyString());
//		int apply_id = jobRequestRepository.selectApply_id();
//		log.warn("[selectApply_idメソッドの正常系テスト_apply_idが取得できなかった時]apply_id:" + apply_id);
//		assertEquals(-1, apply_id);
//
////		log.warn("[selectApply_idメソッドの正常系テスト_apply_idが取得できなかった時]apply_id:" + apply_id);
//	}

	@Test
	void updateJobStatusメソッドの正常系テスト() {
		String apply_id = "1";
		String status = "1";
		String indicate = "特になし";

		int rowNumber = jobRequestRepository.updateJobStatus(apply_id, status, indicate);

		assertEquals(rowNumber, 1);

		log.warn("[updateJobStatusメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void updateJobContentメソッドの正常系テスト() {
		String apply_id = "1";

		JobRequestData data = new JobRequestData();
		data.setApplicant_id("isida@xxx.co.jp");
		data.setContent(Content.TEST);
		data.setCompany_name("updateJobContent株式会社");
		data.setApply_type(Apply_type.PERSONAL);
		data.setDate_activity_from(new Date());
		data.setDate_activity_to(new Date());
		data.setLoc("HCS");
		data.setWay(Way.ABSENCE);
		data.setDate_absence_from(new Date());
		data.setDate_absence_to(new Date());
		data.setLeave_early_date(null);
		data.setDate_absence_from(null);
		data.setRemark("updateJobContentメソッドの正常系テスト");
		data.setRegister_user_id("isida@xxx.co.jp");

		int rowNumber = jobRequestRepository.updateJobContent(data, apply_id);

		assertEquals(rowNumber, 1);

		log.warn("[updateJobContentメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void insertEventメソッドの正常系テスト() {
		EventData data = new EventData();
		data.setCompany_name("insertEvent株式会社");
		data.setBring("筆記用具");
		data.setDatetime(new Date());
		data.setEvent_id("0");
		data.setLoc("HCS");
		data.setContent("insertEventの正常系メソッド");

		String user_id = "isida@xxx.co.jp";

		int rowNumber = jobRequestRepository.insertEvent(data, user_id);

		assertEquals(rowNumber, 1);

		log.warn("[insertEventメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void selectEvent_idメソッドの正常系テスト() {
		int event_id = jobRequestRepository.selectEvent_id();

		assertNotNull(event_id);

		log.warn("[selectEvent_idメソッドの正常系テスト]event_id:" + event_id);
	}

	@Test
	void selectJobHuntingStatusメソッドの正常系テスト() {
		String apply_id = "0";

		String status = jobRequestRepository.selectJobHuntingStatus(apply_id);

		assertNotNull(status);

		log.warn("[selectJobHuntingStatusメソッドの正常系テスト]status:" + status);
	}

	@Test
	void updateStatusOneメソッドの正常系テスト() {
		String apply_id = "0";

		int rowNumber = jobRequestRepository.updateStatusOne(apply_id);

		assertEquals(rowNumber, 1);

		log.warn("[updateStatusOneメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void noticeCourseDirectorメソッドの正常系テスト() {
		String apply_id = "5";

		int rowNumber = jobRequestRepository.noticeCourseDirector(apply_id);

		assertEquals(rowNumber, 1);

		log.warn("[noticeCourseDirectorメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

}
