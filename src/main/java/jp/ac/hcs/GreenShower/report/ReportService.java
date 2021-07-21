package jp.ac.hcs.GreenShower.report;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.report.ReportData.Aptitude_test_detail;
import jp.ac.hcs.GreenShower.report.ReportData.Entry_section;
import jp.ac.hcs.GreenShower.report.ReportData.Interview_detail;
import jp.ac.hcs.GreenShower.report.ReportData.Result_notification;
import jp.ac.hcs.GreenShower.report.ReportData.Test_section;
import jp.ac.hcs.GreenShower.report.ReportData.Test_summary;
import jp.ac.hcs.GreenShower.report.ReportData.Venue_section;


@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<ReportEntity>
	 */
	public Optional<ReportEntity> selectAll() {
		ReportEntity reportEntity;

		try {
			reportEntity = reportRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}
	
	
	/**
	 * 受験報告情報を取得する
	 * 
	 * @return Optional<ReportData>
	 */
	public Optional<ReportData> selectOne(String report_id) {
		ReportData reportEntity;

		try {
			reportEntity = reportRepository.selectOne(report_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}
	
	/**
	 * ユーザマスタに新たなユーザ情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(ReportForm form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = reportRepository.insertOne(refillToReportData(form, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
	}
	
	public boolean update(ReportForm form) {
		ReportData reportData = refillToReportData(form, null);
		int rowNumber = 0;
		try {
			
			rowNumber = reportRepository.updateReportForStudent(reportData);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return rowNumber > 0;
	}
	
	/**
	 * 入力情報をUserData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return UserData
	 */
	private ReportData refillToReportData(ReportForm form, String register_user_id) {
		ReportData data = new ReportData();

//		data.setUser_id();
//		data.setClassroom();
//		data.setClass_number();
//		data.setName();
//		data.setCourse_code();
		data.setReport_id(form.getReport_id());		
		data.setCompany_name(form.getCompany_name());
		data.setCompany_name_kana(form.getCompany_name_kana());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			data.setDatetime(dateFormat.parse(form.getDatetime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		data.setPlace(form.getPlace());
		data.setEntry_section(Entry_section.idOf(form.getEntry_section()));
		data.setEntry_section_other(form.getEntry_section_other());
		data.setVenue_section(Venue_section.idOf(form.getVenue_section()));
		data.setVenue_section_other(form.getVenue_section_other());
		data.setTest_section(Test_section.idOf((form.getTest_section())));
		data.setTest_section_other(form.getEntry_section_other());
		data.setTest_summary(Test_summary.idOf(form.getTest_summary()));
		data.setTest_summary_other(form.getTest_summary_other());
		data.setResult_notification(Result_notification.idOf(form.getResult_notification()));
		data.setResult_notification_day(form.getResult_notification_day());
		data.setAptitude_test_detail(Aptitude_test_detail.idOf(form.getAptitude_test_detail()));
		data.setAptitude_test_detail_other(form.getAptitude_test_detail_other());
		data.setInterview_detail(Interview_detail.idOf(form.getInterview_detail()));
		data.setInterview_detail_other(form.getInterview_detail_other());
		data.setInterview_number(form.getInterview_number());
		data.setInterviewer_number(form.getInterviewer_number());		
		data.setInterviewer_position(form.getInterviewer_position());
		data.setInterview_time(form.getInterview_time());		
		data.setTheme(form.getTheme());
		data.setQuestion_contents(form.getQuestion_contents());
		data.setReport_status(null);
		data.setRequest_date(form.getRequest_date());
		data.setRegistered_user_id(register_user_id);
		data.setRemarks(null);

		return data;
	}

	
	/**
	 * 受験報告情報ステータス変更用
	 * @param report_id
	 * @param report_status
	 */
	public boolean updateStatus(String report_id, String report_status) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = reportRepository.updateStatus(report_id, report_status);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
		
	}
	
	/**
	 * タスク情報をCSVファイルとしてサーバに保存する.
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void reportListCsvOut() throws DataAccessException {
		reportRepository.reportlistCsvOut();
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}

}
