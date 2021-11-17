package jp.ac.hcs.GreenShower.job.report;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class JobReportServiceTest {

	@Autowired
	JobReportService jobReportService;
	@SpyBean
	JobReportRepository jobReportRepository;
	
	@Test
	void selectAllReportsの正常系テスト_userDataIsEmpty() {
		//1.ready
		String role = "ROLE_EMPTY";
		String user_id = "empty@xxx.co.jp";
		//2.do
		Optional<JobReportEntity> jobReportEntity = jobReportService.selectAllReports(user_id,role);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_userDataIsEmpty]jobReportEntity:"+jobReportEntity);
	}
	@Test
	void selectAllReportsの正常系テスト_ROLE_STUDENT() {
		//1.ready
		String role = "ROLE_STUDENT";
		String user_id = "isida@xxx.co.jp";
		//2.do
		Optional<JobReportEntity> jobReportEntity = jobReportService.selectAllReports(user_id,role);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_ROLE_STUDENT]jobReportEntity:"+jobReportEntity);
	}
	
	@Test
	void selectAllReportsの正常系テスト_jobReportEntityIsEmpty() {
		//1.ready
		String role = "ROLE_STUDENT";
		String user_id = "yamada@xxx.co.jp";
		//2.do
		Optional<JobReportEntity> jobReportEntity = jobReportService.selectAllReports(user_id,role);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_jobReportEntityIsEmpty]jobReportEntity:"+jobReportEntity);
	}

	@Test
	void selectAllReportsの正常系テスト_ROLE_TEACHER() {
		//1.ready
		String role = "ROLE_TEACHER";
		String user_id = "abe@xxx.co.jp";
		//2.do
		Optional<JobReportEntity> jobReportEntity = jobReportService.selectAllReports(user_id,role);
		//3.assert
		assertNotNull(jobReportEntity);
		//4.logs
		log.warn("[selectAllReportsの正常系テスト_ROLE_TEACHER]jobReportEntity:"+jobReportEntity);
	}

	@Test
	void selectAllReportsの異常系テスト() throws DataAccessException{
		//1.ready
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).selectAllReports(any());
		//2.do
		Optional<JobReportEntity> jobReportEntity = jobReportService.selectAllReports("yamada@xxx.co.jp","ROLE_STUDENT");
		//3.assert
		assertEquals(jobReportEntity,Optional.empty());
		//4.logs
		log.warn("[selectAllReportsの異常系テスト]jobReportEntity:"+jobReportEntity);
	}
	
	@Test
	void selectOneの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		Optional<JobReportData> jobReportData = jobReportService.selectOne(apply_id);
		//3.assert
		assertNotNull(jobReportData);
		//4.logs
		log.warn("[selectOneの正常系テスト]jobReportData:"+jobReportData);
	}
	
	@Test
	void selectOneの異常系テスト() throws DataAccessException{
		//1.ready
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).selectOne(any());
		//2.do
		Optional<JobReportData> jobReportData = jobReportService.selectOne("1");
		//3.assert
		assertEquals(jobReportData,Optional.empty());
		//4.logs
		log.warn("[selectOneの異常系テスト]jobReportData:"+jobReportData);
	}
	
	@Test
	void selectPersonalInfoApplyの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		Optional<UserData> userData = jobReportService.selectPersonalInfoApply(apply_id);
		//3.assert
		assertNotNull(userData);
		//4.logs
		log.warn("[selectPersonalInfoApplyの正常系テスト]userData:"+userData);
	}
	
	@Test
	void selectPersonalInfoApplyの異常系テスト() throws DataAccessException{
		//1.ready
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).selectPersonalInfoApply(any());
		//2.do
		Optional<UserData> userData = jobReportService.selectPersonalInfoApply("1");
		//3.assert
		assertEquals(userData,Optional.empty());
		//4.logs
		log.warn("[selectPersonalInfoApplyの異常系テスト]userData:"+userData);
	}

	@Test
	void selectJobHuntingStatusの正常系テスト() {
		//1.ready
		String apply_id = "1";
		//2.do
		String status = jobReportService.selectJobHuntingStatus(apply_id);
		//3.assert
		assertNotNull(status);
		//4.logs
		log.warn("[selectPersonalInfoApplyの正常系テスト]status:"+status);
	}
	
	@Test
	void selectJobHuntingStatusの異常系テスト() throws DataAccessException{
		//1.ready
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).selectJobHuntingStatus(any());
		//2.do
		String status = jobReportService.selectJobHuntingStatus("1");
		//3.assert
		assertNull(status);
		//4.logs
		log.warn("[selectPersonalInfoApplyの異常系テスト]status:"+status);
	}

	@Test
	void insertの正常系テスト_成功() {
		//1.ready
		String register_user_id="abe@xxx.co.jp";
		JobReportForm form = new JobReportForm();
		form.setApply_id("6");
		form.setAdvance_or_retreat(true);
		form.setRemark("test");
		form.setStatus(null);
		form.setIndicate(null);
		//2.do
		boolean isSuccess = jobReportService.insert(form,register_user_id);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[insertの正常系テスト_成功]isSuccess:"+isSuccess);
	}

	@Test
	void insertの正常系テスト_失敗() {
		//1.ready
		String register_user_id="abe@xxx.co.jp";
		JobReportForm form = new JobReportForm();
		form.setApply_id("3");
		form.setAdvance_or_retreat(true);
		form.setRemark("test");
		form.setStatus(null);
		form.setIndicate(null);
		doReturn(0).when(jobReportRepository).insertOne(any());
		//2.do
		boolean isSuccess = jobReportService.insert(form,register_user_id);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[insertの正常系テスト_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void insertの異常系テスト() throws DataAccessException{
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("6");
		form.setAdvance_or_retreat(true);
		form.setRemark("test");
		form.setStatus(null);
		form.setIndicate(null);
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).insertOne(any());
		//2.do
		boolean isSuccess = jobReportService.insert(form,"abe@xxx.co.jp");
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[insertの異常系テスト]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusの正常系テスト_成功() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setStatus("6");
		form.setIndicate("指摘事項");
		//2.do
		boolean isSuccess = jobReportService.updateStatus(form);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[updateStatusの正常系テスト_成功]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusの正常系テスト_失敗() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setStatus("6");
		form.setIndicate("指摘事項");
		doReturn(0).when(jobReportRepository).updateStatus(any());
		//2.do
		boolean isSuccess = jobReportService.updateStatus(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateStatusの正常系テスト_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusの異常系テスト() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setStatus("6");
		form.setIndicate("指摘事項");
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).updateStatus(any());
		//2.do
		boolean isSuccess = jobReportService.updateStatus(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateStatusの異常系テスト]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateAdvance_or_retreatの正常系テスト_進める_成功() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setAdvance_or_retreat(true);
		//2.do
		boolean isSuccess = jobReportService.updateAdvance_or_retreat(form);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[updateAdvance_or_retreatの正常系テスト_進める_成功]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateAdvance_or_retreatの正常系テスト_進める_失敗() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setAdvance_or_retreat(true);
		doReturn(0).when(jobReportRepository).updateAdvance_or_retreat_to_true(any());
		//2.do
		boolean isSuccess = jobReportService.updateAdvance_or_retreat(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateAdvance_or_retreatの正常系テスト_進める_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateAdvance_or_retreatの正常系テスト_進めない_成功() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setAdvance_or_retreat(false);
		//2.do
		boolean isSuccess = jobReportService.updateAdvance_or_retreat(form);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[updateAdvance_or_retreatの正常系テスト_進めない_成功]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateAdvance_or_retreatの正常系テスト_進めない_失敗() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setAdvance_or_retreat(false);
		doReturn(0).when(jobReportRepository).updateAdvance_or_retreat_to_false(any());
		//2.do
		boolean isSuccess = jobReportService.updateAdvance_or_retreat(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateAdvance_or_retreatの正常系テスト_進めない_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateAdvance_or_retreatの異常系テスト() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setAdvance_or_retreat(true);
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).updateAdvance_or_retreat_to_true(any());
		//2.do
		boolean isSuccess = jobReportService.updateAdvance_or_retreat(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateAdvance_or_retreatの異常系テスト]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateRemarkの正常系テスト_成功() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setRemark("備考");
		//2.do
		boolean isSuccess = jobReportService.updateRemark(form);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[updateRemarkの正常系テスト_成功]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateRemarkの正常系テスト_失敗() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setRemark("備考");
		doReturn(0).when(jobReportRepository).updateRemark(anyString(),anyString());
		//2.do
		boolean isSuccess = jobReportService.updateRemark(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateRemarkの正常系テスト_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateRemarkの異常系テスト() {
		//1.ready
		JobReportForm form = new JobReportForm();
		form.setApply_id("2");
		form.setRemark("備考");
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).updateRemark(anyString(),anyString());
		//2.do
		boolean isSuccess = jobReportService.updateRemark(form);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateRemarkの異常系テスト]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusFixedの正常系テスト_成功() {
		//1.ready
		JobReportForm form = new JobReportForm();
		String Apply_id="2";
		//2.do
		boolean isSuccess = jobReportService.updateStatusFixed(Apply_id);
		//3.assert
		assertTrue(isSuccess);
		//4.logs
		log.warn("[updateStatusFixedの正常系テスト_成功]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusFixedの正常系テスト_失敗() {
		//1.ready
		String Apply_id="2";
		doReturn(0).when(jobReportRepository).updateStatusOne(anyString());
		//2.do
		boolean isSuccess = jobReportService.updateStatusFixed(Apply_id);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateStatusFixedの正常系テスト_失敗]isSuccess:"+isSuccess);
	}
	
	@Test
	void updateStatusFixedの異常系テスト() {
		//1.ready
		String Apply_id="2";
		doThrow(new DataAccessResourceFailureException("")).when(jobReportRepository).updateStatusOne(anyString());
		//2.do
		boolean isSuccess = jobReportService.updateStatusFixed(Apply_id);
		//3.assert
		assertFalse(isSuccess);
		//4.logs
		log.warn("[updateStatusFixedの異常系テスト]isSuccess:"+isSuccess);
	}
}
