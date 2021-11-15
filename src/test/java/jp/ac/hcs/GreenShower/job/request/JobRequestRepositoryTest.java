package jp.ac.hcs.GreenShower.job.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JobRequestRepositoryTest {

	@Autowired
	JobRequestRepository jobRequestRepository;

	@Test
	void selectAllRequestsの正常系テスト() {
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
	void selectOneの正常系テスト() {
		String apply_id = "1";

		JobRequestData jobRequestData = jobRequestRepository.selectOne(apply_id);

		assertNotNull(jobRequestData);

		log.warn("[selectOneメソッドの正常系テスト]jobRequestData:" + jobRequestData.toString());
	}

	@Test
	void selectPersonalInfoの正常系テスト() {
		String user_id = "isida@xxx.co.jp";

		UserData userData = jobRequestRepository.selectPersonalInfo(user_id);

		assertNotNull(userData);

		log.warn("[selectPersonalInfoメソッドの正常系テスト]userData:" + userData.toString());
	}

	@Test
	void API用のselectPersonalInfoの正常系テスト() {
		String classroom = "S3A1";
		String class_number = "01";

		UserData userData = jobRequestRepository.selectPersonalInfo(classroom, class_number);

		assertNotNull(userData);

		log.warn("[API用のselectPersonalInfoメソッドの正常系テスト]userData:" + userData.toString());
	}

	@Test
	void testSelectStudentsNumber() {
		fail("まだ実装されていません");
	}

	@Test
	void testInsertOne() {
		fail("まだ実装されていません");
	}

	@Test
	void testApply_id_get() {
		fail("まだ実装されていません");
	}

	@Test
	void testUpdateJobStatus() {
		fail("まだ実装されていません");
	}

	@Test
	void testUpdateJobContent() {
		fail("まだ実装されていません");
	}

	@Test
	void testInsertEvent() {
		fail("まだ実装されていません");
	}

	@Test
	void testEvent_id_get() {
		fail("まだ実装されていません");
	}

	@Test
	void testSelectJobHuntingStatus() {
		fail("まだ実装されていません");
	}

	@Test
	void testUpdateStatusOne() {
		fail("まだ実装されていません");
	}

	@Test
	void testObject() {
		fail("まだ実装されていません");
	}

	@Test
	void testGetClass() {
		fail("まだ実装されていません");
	}

	@Test
	void testHashCode() {
		fail("まだ実装されていません");
	}

	@Test
	void testEquals() {
		fail("まだ実装されていません");
	}

	@Test
	void testClone() {
		fail("まだ実装されていません");
	}

	@Test
	void testToString() {
		fail("まだ実装されていません");
	}

	@Test
	void testNotify() {
		fail("まだ実装されていません");
	}

	@Test
	void testNotifyAll() {
		fail("まだ実装されていません");
	}

	@Test
	void testWait() {
		fail("まだ実装されていません");
	}

	@Test
	void testWaitLong() {
		fail("まだ実装されていません");
	}

	@Test
	void testWaitLongInt() {
		fail("まだ実装されていません");
	}

	@Test
	void testFinalize() {
		fail("まだ実装されていません");
	}

}
