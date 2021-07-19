package jp.ac.hcs.GreenShower.report;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.user.UserFormForInsert;


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
	 * 受験報告情報を全件取得する
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
	public boolean insert(UserFormForInsert form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = reportRepository.insertOne(refillToReportData(form, register_user_id));
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
	private ReportData refillToReportData(UserFormForInsert form, String register_user_id) {
		ReportData data = new ReportData();

		data.getReport_id();
		data.getUser_id();
		data.getClassroom();
		data.getClass_number();
		data.getName();
		data.getCourse_code();
		data.getCompany_name();
		data.getCompany_name_kana();
		data.getDatetime();
		data.getPlace();
		data.getEntry_section();
		data.getEntry_section_other();
		data.getVenue_section();
		data.getVenue_section_other();
		data.getTest_section();
		data.getTest_section_other();
		data.getTest_summary();
		data.getTest_summary_other();
		data.getResult_notification();
		data.getResult_notification_day();
		data.getAptitude_test_detail();
		data.getAptitude_test_detail_other();
		data.getInterview_detail();
		data.getInterview_detail_other();
		data.getInterview_number();
		data.getInterviewer_number();
		data.getInterviewer_position();
		data.getInterview_time();
		data.getTheme();
		data.getQuestion_contents();
		data.getReport_status();
		data.getRequest_date();
		data.getRegistered_user_id();
		data.getRemarks();
		data.setRegistered_user_id(register_user_id);

		return data;
	}

}
