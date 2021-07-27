package jp.ac.hcs.GreenShower.report;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ReportRepositoryTest {

	@Autowired
	ReportRepository reportRepository;
	@Test
	void selectAllの正常系テスト() {
		// 1.Ready

		// 2.Do
		ReportEntity reportEntity = reportRepository.selectAll();
		// 3.Assert
		assertNotNull(reportEntity);
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]reportEntity:" + reportEntity.toString());
	}

	@Test
	void testSelectOne() {
		// 1.Ready
		String report_id = "1";
		// 2.Do
		ReportData data = reportRepository.selectOne(report_id);
		// 3.Assert
		assertNotNull(data);
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]reportData:" + data.toString());
	}
//
//	@Test
//	void testInsertOne() throws ParseException {
//		// 1.Ready
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				ReportData data = new ReportData();
//				String register_user_id = "1";
//				data.setReport_id(1);
//				data.setJob_number("111");
//				data.setCompany_name("亜亜亜");
//				data.setCompany_name_kana("あああ");
//				data.setDatetime(sdf.parse("2021-07-17 00:00:00.0"));
//				data.setPlace("札幌");
//				data.setEntry_section(1);
//				data.setEntry_section_other("その他");
//				data.setVenue_section(1);
//				data.setVenue_section_other("その他");
//				data.setTest_section(1);
//				data.setTest_section_other("その他");
//				data.setTest_final(true);
//				data.setTest_summary(1);
//				data.setTest_summary_other("その他");
//				data.setResult_notification(1);
//				data.setResult_notification_day(1);
//				data.setSuccess_only(true);
//				data.setAptitude_test_detail(1);
//				data.setAptitude_test_detail_other("その他");
//				data.setInterview_detail(1);
//				data.setInterview_detail_other("その他");
//				data.setInterview_number(1);
//				data.setInterviewer_number(1);
//				data.setInterviewer_position("課長");
//				data.setInterview_time(30);
//				data.setTheme("テーマ");
//				data.setQuestion_contents("出題内容");
//				data.setRequest_date(sdf.parse("2021-07-15"));
//				// 2.Do
//				int reportEntity = reportRepository.insertOne(data);
//				// 3.Assert
//				assertNotNull(reportEntity);
//				// 4.Logs
//				log.warn("[selectAllメソッドの正常系テスト]reportEntity:" + reportEntity.toString());
//	}

//	@Test
//	void testUpdateReportForStudent() {
//		fail("まだ実装されていません");
//	}

	@Test
	void testUpdateStatus() {
		// 1.Ready
		String report_id = "1";
		String report_status = "1";
		// 2.Do
		int rowNumber = reportRepository.updateStatus(report_id,report_status);
		// 3.Assert
		assertEquals(1,rowNumber);
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]rowNumber:" + rowNumber);
	}

	@Test
	void testReportlistCsvOut() {
		// 1.Ready
		// 2.Do
		reportRepository.reportlistCsvOut();
		// 3.Assert
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]");
	}

}
