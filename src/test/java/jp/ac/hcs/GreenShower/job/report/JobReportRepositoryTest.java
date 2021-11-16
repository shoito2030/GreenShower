package jp.ac.hcs.GreenShower.job.report;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class JobReportRepositoryTest {
	@Autowired
	JobReportRepository jobReportRepository;
	
	
	@Test
	void SelectAllReportsの正常系テスト() {
		//1.ready
		String classroom = "S3A1";
		//2.do
		JobReportEntity jobReportEntity = jobReportRepository.selectAllReports(classroom);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト]jobReportEntity:" + jobReportEntity);
	}
	
	@Test
	void SelectAllReportsの異常系テスト() {
		//1.ready
		String classroom = "S3A4";
		//2.do
		JobReportEntity jobReportEntity = jobReportRepository.selectAllReports(classroom);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト]jobReportEntity:" + jobReportEntity);
	}

	@Test
	void SelectOneの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		JobReportData jobReportData = jobReportRepository.selectOne(apply_id);
		//3.assert
		assertNotNull(jobReportData);
		//4.logs
		log.warn("[selectAllOneの正常系テスト]jobReportData:" + jobReportData);
	}

	@Test
	void SelectPersonalInfoApplyの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		UserData data = jobReportRepository.selectPersonalInfoApply(apply_id);
		//3.assert
		assertNotNull(data);
		//4.logs
		log.warn("[SelectPersonalInfoApplyの正常系テスト]UserData:" + data);
	}

	@Test
	void SelectPersonalInfoUserIDの正常系テスト() {
		//1.ready
		String user_id = "isida@xxx.co.jp";
		//2.do
		UserData data = jobReportRepository.selectPersonalInfoUserID(user_id);
		//3.assert
		assertNotNull(data);
		//4.logs
		log.warn("[SelectPersonalInfoUserIDの正常系テスト]UserData:" + data);
	}

	@Test
	void SelectJobHuntingStatusの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		String status = jobReportRepository.selectJobHuntingStatus(apply_id);
		//3.assert
		assertNotNull(status);
		//4.logs
		log.warn("[SelectJobHuntingStatusの正常系テスト]status:" + status);
	}

	@Test
	void InsertOneの正常系テスト() {
		//1.ready
		JobReportData data = new JobReportData();
		data.setAdvance_or_retreat(true);
		data.setRemark("あいうえお");
		data.setApply_id("5");
		data.setRegister_user_id("isida@xxx.co.jp");
		//2.do
		int rowNumber = jobReportRepository.insertOne(data);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[InsertOneの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void UpdateAdvance_or_retreat_to_false() {
		//1.ready
		String apply_id = "1";
		//2.do
		int rowNumber = jobReportRepository.updateAdvance_or_retreat_to_false(apply_id);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[UpdateAdvance_or_retreat_to_falseの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void UpdateAdvance_or_retreat_to_true() {
		//1.ready
		String apply_id = "1";
		//2.do
		int rowNumber = jobReportRepository.updateAdvance_or_retreat_to_true(apply_id);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[UpdateAdvance_or_retreat_to_trueの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void UpdateRemark() {
		//1.ready
		String apply_id = "1";
		String remark = "テストテストテスト";
		//2.do
		int rowNumber = jobReportRepository.updateRemark(apply_id, remark);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[UpdateRemarkの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void UpdateStatusOne() {
		//1.ready
		String apply_id = "1";
		//2.do
		int rowNumber = jobReportRepository.updateStatusOne(apply_id);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[UpdateStatusOneの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void UpdateStatus() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("5");
		form.setIndicate("aaa");
		form.setStatus("5");
		//2.do
		int rowNumber = jobReportRepository.updateStatus(form);
		//3.assert
		assertNotNull(rowNumber);
		//4.logs
		log.warn("[UpdateStatusの正常系テスト]rowNumber:" + rowNumber);
	}

}
