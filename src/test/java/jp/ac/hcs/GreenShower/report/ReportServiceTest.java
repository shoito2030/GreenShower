package jp.ac.hcs.GreenShower.report;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ReportServiceTest {

	@Autowired
	ReportService reportService;

	@SpyBean
	ReportRepository reportRepository;

	@Test
	void selectAllの正常系テスト() {
		// 1.Ready

		// 2.Do
		Optional<ReportEntity> reportEntity = reportService.selectAll();
		// 3.Assert
		assertNotNull(reportEntity);
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]reportEntity:" + reportEntity.toString());
	}
	@Test
	void selectAllの異常系テスト() {
		// 1.Ready
		doThrow(new DataAccessResourceFailureException("")).when(reportRepository).selectAll();
		// 2.Do
		Optional<ReportEntity> reportEntity = reportService.selectAll();
		// 3.Assert
		assertNotNull(reportEntity);
		// 4.Logs
		log.warn("[selectAllメソッドの異常系テスト]reportEntity:" + reportEntity);
	}

	@Test
	void selectOneの正常系テスト() {
		// 1.Ready
		String report_id = "1";
		// 2.Do
		Optional<ReportData> reportEntity = reportService.selectOne(report_id);
		// 3.Assert
		assertNotNull(reportEntity);
		// 4.Logs
		log.warn("[selectOneメソッドの正常系テスト]reportEntity:" + reportEntity.toString());
	}

	@Test
	void selectOneの異常系テスト() {
		// 1.Ready
		String report_id = "1";
		doThrow(new DataAccessResourceFailureException("")).when(reportRepository).selectOne(anyString());
		// 2.Do
		Optional<ReportData> reportEntity = reportService.selectOne(report_id);
		// 3.Assert
		assertNotNull(reportEntity);
		// 4.Logs
		log.warn("[selectOneメソッドの異常系テスト]reportEntity:" + reportEntity);
	}

	@Test
	void insertの正常系テスト() throws ParseException {
		// 1.Ready
		ReportForm form = new ReportForm();
		String register_user_id = "1";
		form.setReport_id(1);
		form.setJob_number("111");
		form.setCompany_name("亜亜亜");
		form.setCompany_name_kana("あああ");
		form.setDatetime("2021-07-17 00:00:00.0");
		form.setPlace("札幌");
		form.setEntry_section(1);
		form.setEntry_section_other("その他");
		form.setVenue_section(1);
		form.setVenue_section_other("その他");
		form.setTest_section(1);
		form.setTest_section_other("その他");
		form.setTest_final(true);
		form.setTest_summary(1);
		form.setTest_summary_other("その他");
		form.setResult_notification(1);
		form.setResult_notification_day(1);
		form.setSuccess_only(true);
		form.setAptitude_test_detail(1);
		form.setAptitude_test_detail_other("その他");
		form.setInterview_detail(1);
		form.setInterview_detail_other("その他");
		form.setInterview_number(1);
		form.setInterviewer_number(1);
		form.setInterviewer_position("課長");
		form.setInterview_time(30);
		form.setTheme("テーマ");
		form.setQuestion_contents("出題内容");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		form.setRequest_date(sdf.parse("2021-07-15"));
		// 2.Do
		boolean result = reportService.insert(form,register_user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[insertメソッドの正常系テスト]result:" + result);
	}

	@Test
	void insertの異常系テスト_DataAccessException() throws ParseException {
		// 1.Ready
		ReportForm form = new ReportForm();
		String register_user_id = "1";
		form.setReport_id(1);
		form.setJob_number("111");
		form.setCompany_name("亜亜亜");
		form.setCompany_name_kana("あああ");
		form.setDatetime("2021-07-17 00:00:00.0");
		form.setPlace("札幌");
		form.setEntry_section(1);
		form.setEntry_section_other("その他");
		form.setVenue_section(1);
		form.setVenue_section_other("その他");
		form.setTest_section(1);
		form.setTest_section_other("その他");
		form.setTest_final(true);
		form.setTest_summary(1);
		form.setTest_summary_other("その他");
		form.setResult_notification(1);
		form.setResult_notification_day(1);
		form.setSuccess_only(true);
		form.setAptitude_test_detail(1);
		form.setAptitude_test_detail_other("その他");
		form.setInterview_detail(1);
		form.setInterview_detail_other("その他");
		form.setInterview_number(1);
		form.setInterviewer_number(1);
		form.setInterviewer_position("課長");
		form.setInterview_time(30);
		form.setTheme("テーマ");
		form.setQuestion_contents("出題内容");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		form.setRequest_date(sdf.parse("2021-07-15"));

		doThrow(new DataAccessResourceFailureException("")).when(reportRepository).insertOne(any());
		// 2.Do
		boolean result = reportService.insert(form,register_user_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[insertメソッドの異常系テストDataAccessException]result:" + result);
	}

	@Test
	void insertの異常系テスト_ParseException() throws ParseException {
		// 1.Ready
		ReportForm form = new ReportForm();
		String register_user_id = "1";
		form.setReport_id(1);
		form.setJob_number("111");
		form.setCompany_name("亜亜亜");
		form.setCompany_name_kana("あああ");
		form.setDatetime("aaaaaa");
		form.setPlace("札幌");
		form.setEntry_section(1);
		form.setEntry_section_other("その他");
		form.setVenue_section(1);
		form.setVenue_section_other("その他");
		form.setTest_section(1);
		form.setTest_section_other("その他");
		form.setTest_final(true);
		form.setTest_summary(1);
		form.setTest_summary_other("その他");
		form.setResult_notification(1);
		form.setResult_notification_day(1);
		form.setSuccess_only(true);
		form.setAptitude_test_detail(1);
		form.setAptitude_test_detail_other("その他");
		form.setInterview_detail(1);
		form.setInterview_detail_other("その他");
		form.setInterview_number(1);
		form.setInterviewer_number(1);
		form.setInterviewer_position("課長");
		form.setInterview_time(30);
		form.setTheme("テーマ");
		form.setQuestion_contents("出題内容");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		form.setRequest_date(sdf.parse("2021-07-15"));
		// 2.Do
		boolean result = reportService.insert(form,register_user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[insertメソッドの異常系テスト_ParseException]result:" + result);
	}

	//	@Test
	//	void updateの正常系テスト() throws ParseException {
	//		// 1.Ready
	//		ReportForm form = new ReportForm();
	//		form.setReport_id(1);
	//		form.setJob_number("111");
	//		form.setCompany_name("亜亜亜");
	//		form.setCompany_name_kana("あああ");
	//		form.setDatetime("2021-07-17 00:00:00.0");
	//		form.setPlace("札幌");
	//		form.setEntry_section(1);
	//		form.setEntry_section_other("その他");
	//		form.setVenue_section(1);
	//		form.setVenue_section_other("その他");
	//		form.setTest_section(1);
	//		form.setTest_section_other("その他");
	//		form.setTest_final(true);
	//		form.setTest_summary(1);
	//		form.setTest_summary_other("その他");
	//		form.setResult_notification(1);
	//		form.setResult_notification_day(1);
	//		form.setSuccess_only(true);
	//		form.setAptitude_test_detail(1);
	//		form.setAptitude_test_detail_other("その他");
	//		form.setInterview_detail(1);
	//		form.setInterview_detail_other("その他");
	//		form.setInterview_number(1);
	//		form.setInterviewer_number(1);
	//		form.setInterviewer_position("課長");
	//		form.setInterview_time(30);
	//		form.setTheme("テーマ");
	//		form.setQuestion_contents("出題内容");
	//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//		form.setRequest_date(sdf.parse("2021-07-15"));
	//		// 2.Do
	//		boolean result = reportService.update(form);
	//		// 3.Assert
	//		assertEquals(true, result);
	//		// 4.Logs
	//		log.warn("[updateメソッドの正常系テスト]result:" + result);
	//	}

	@Test
	void updateStatusの正常系テスト() {
		// 1.Ready
		String report_id = "1";
		String report_status = "1";
		// 2.Do
		boolean result = reportService.updateStatus(report_id,report_status);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[updateStatusメソッドの正常系テスト]result:" + result);
	}

	@Test
	void updateStatusの異常系テスト() {
		// 1.Ready
		String report_id = "1";
		String report_status = "1";
		doThrow(new DataAccessResourceFailureException("")).when(reportRepository).updateStatus(report_id, report_status);
		// 2.Do
		boolean result = reportService.updateStatus(report_id,report_status);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[updateStatusメソッドの異常系テスト]result:" + result);
	}

	@Test
	void reportListCsvOutの正常系テスト() {
		// 1.Ready
		// 2.Do
		reportService.reportListCsvOut();
		// 3.Assert
		// 4.Logs
		log.warn("[reportListCsvOutの正常系テスト]");
	}

	@Test
	void getFileの正常系テスト() throws IOException {
		// 1.Ready
				String fileName = "target/tasklist.csv";
				// 2.Do
				byte[] bytes = reportService.getFile(fileName);
				// 3.Assert
				// 4.Logs
				log.warn("[getFileの正常系テスト]");
	}

}
