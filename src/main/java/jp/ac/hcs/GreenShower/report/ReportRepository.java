package jp.ac.hcs.GreenShower.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepository {

	/** SQL 全件取得（クラスと出席番号の昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM report order by classroom, class_number";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM report WHERE report_id = ?";
	
	/** SQL 1件追加  */
	private static final String SQL_INSERT_ONE = 
			"INSERT INTO report (report_id, user_id, classroom, class_number, name, course_code, company_name,"
			+ "company_name_kana, datetime, place, entry_section, entry_section_other, venue_section, venue_section_other, test_section,"
			+ "test_section_other, test_summary, test_summary_other, result_notification, result_notification_day, aptitude_test_detail,"
			+ "aptitude_test_detail_other, interview_detail, interview_detail_other, interview_number, interviewer_number, interviewer_position,"
			+ "interview_time, theme, question_contents, request_date, registered_user_id)"
			+ "VALUES((SELECT MAX(report_id) + 1 FROM report),? , (SELECT classroom FROM users WHERE user_id = ? ), (SELECT class_number FROM users WHERE user_id = ? )"
			+ ", (SELECT name FROM users WHERE user_id = ?), (SELECT SUBSTRING(classroom, 1)  FROM users WHERE user_id = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//	private static final String SQL_INSERT_ONE = 
//			"INSERT INTO report VALUES((SELECT MAX(report_id) + 1 FROM report),? , (SELECT classroom FROM users WHERE user_id = ? ), (SELECT class_number FROM users WHERE user_id = ? )"
//			+ ", (SELECT name FROM users WHERE user_id = ?), (SELECT SUBSTRING(classroom, 1)  FROM users WHERE user_id = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/** SQL 1件更新  */
	private static final String SQL_UPDATE_ONE = "UPDATE report SET company_name = ?,"
			+ "company_name_kana = ?, datetime = ?, place = ?, entry_section = ?, entry_section_other = ?, venue_section = ?, venue_section_other = ?, test_section = ?,"
			+ "test_section_other = ?, test_summary = ?, test_summary_other = ?, result_notification = ?, result_notification_day = ?, aptitude_test_detail = ?,"
			+ "aptitude_test_detail_other = ?, interview_detail = ?, interview_detail_other = ?, interview_number = ?, interviewer_number = ?, interviewer_position = ?,"
			+ "interview_time = ?, theme = ?, question_contents = ?, request_date = ? WHERE report_id = ?";
	
	/** SQL 1件更新  */
	private static final String SQL_UPDATE_REPORT_STATUS = "UPDATE report SET report_status = ? WHERE report_id = ?";
	
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	/**
	 * reportテーブルから全データを取得.
	 * 
	 * @return ReportEntity
	 * @throws DataAccessException
	 */
	public ReportEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		ReportEntity reportEntity = mappingSelectResult(resultList);
		return reportEntity;
	}

	/**
	 * reportテーブルからレポートIDをキーにデータを1件を取得.
	 * @param report_id 検索するレポートID
	 * @return ReportData
	 * @throws DataAccessException
	 */
	public ReportData selectOne(String report_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, report_id);
		ReportEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		ReportData data = entity.getReportlist().get(0);
		return data;
	}
	
	/**
	 * reportテーブルにデータを1件追加する.
	 * 
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(ReportData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE, 
				
				data.getUser_id(),
				
				// classroom,class_number,name,class_cordはユーザIDと紐づけてuserテーブルから取得する
				data.getUser_id(),
				data.getUser_id(),
				data.getUser_id(),
				data.getUser_id(),
				
				data.getCompany_name(),
				data.getCompany_name_kana(),
				data.getDatetime(),
				data.getPlace(),
				data.getEntry_section(),
				data.getEntry_section_other(),
				data.getVenue_section(),
				data.getVenue_section_other(),
				data.getTest_section(),
				data.getTest_section_other(),
				data.getTest_summary(),
				data.getTest_summary_other(),
				data.getResult_notification(),
				data.getResult_notification_day(),
				data.getAptitude_test_detail(),
				data.getAptitude_test_detail_other(),
				data.getInterview_detail(),
				data.getInterview_detail_other(),
				data.getInterview_number(),
				data.getInterviewer_number(),
				data.getInterviewer_position(),
				data.getInterview_time(),
				data.getTheme(),
				data.getQuestion_contents(),
				data.getRequest_date(),
				data.getRegistered_user_id());

		return rowNumber;
	}
	
	
	/**
	 * reportテーブルから取得したデータをReportEntity形式にマッピングする.
	 * 
	 * @param resultList reportテーブルから取得したデータ
	 * @return ReportEntity
	 */
	private ReportEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		ReportEntity entity = new ReportEntity();

		for (Map<String, Object> map : resultList) {
			ReportData data = new ReportData();
			
			data.setReport_id((int) map.get("report_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setClassroom((String) map.get("classroom"));
			data.setClass_number((String) map.get("class_number"));
			data.setName((String) map.get("name"));
			data.setCourse_code((String) map.get("course_code"));
			data.setCompany_name((String) map.get("company_name"));
			data.setCompany_name_kana((String) map.get("company_name_kana"));
			data.setDatetime((Date) map.get("datetime"));
			data.setPlace((String) map.get("place"));
			data.setEntry_section((String) map.get("entry_section"));
			data.setEntry_section_other((String) map.get("entry_section_other"));
			data.setVenue_section((String) map.get("venue_section"));
			data.setVenue_section_other((String) map.get("venue_section_other"));
			data.setTest_section((String) map.get("test_section"));
			data.setTest_section_other((String) map.get("test_section_other"));
			data.setTest_summary((String) map.get("test_summary"));
			data.setTest_summary_other((String) map.get("test_summary_other"));
			data.setResult_notification((String) map.get("result_notification"));
//			data.setResult_notification_day((String) map.get("result_notification_day"));
			data.setAptitude_test_detail((String) map.get("aptitude_test_detail"));
			data.setAptitude_test_detail_other((String) map.get("aptitude_test_detail_other"));
			data.setInterview_detail((String) map.get("interview_detail"));
			data.setInterview_detail_other((String) map.get("interview_detail_other"));
			data.setInterview_number((int) map.get("interview_number"));
			data.setInterviewer_number((int) map.get("interviewer_number"));
			data.setInterviewer_position((String) map.get("interviewer_position"));
			data.setInterview_time((int) map.get("interview_time"));
			data.setTheme((String) map.get("theme"));
			data.setQuestion_contents((String) map.get("question_contents"));
			data.setReport_status((String) map.get("report_status"));
			data.setRegistered_date((Date) map.get("registered_date"));
			data.setRequest_date((Date) map.get("request_date"));
			data.setRegistered_user_id((String) map.get("registered_user_id"));
			data.setRemarks((String) map.get("remarks"));

			entity.getReportlist().add(data);
		}
		return entity;
	}

	/**
	 * 学生が報告情報を編集する
	 * @param data
	 * @return
	 */
	public int updateReportForStudent(ReportData data) {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				data.getCompany_name(),
				data.getCompany_name_kana(),
				data.getDatetime(),
				data.getPlace(),
				data.getEntry_section(),
				data.getEntry_section_other(),
				data.getVenue_section(),
				data.getVenue_section_other(),
				data.getTest_section(),
				data.getTest_section_other(),
				data.getTest_summary(),
				data.getTest_summary_other(),
				data.getResult_notification(),
				data.getResult_notification_day(),
				data.getAptitude_test_detail(),
				data.getAptitude_test_detail_other(),
				data.getInterview_detail(),
				data.getInterview_detail_other(),
				data.getInterview_number(),
				data.getInterviewer_number(),
				data.getInterviewer_position(),
				data.getInterview_time(),
				data.getTheme(),
				data.getQuestion_contents(),
				data.getRequest_date(),
				data.getReport_id());
		return rowNumber;
	}
	
	/**
	 * 
	 * @param report_id
	 * @param report_status
	 * @return
	 */
	public int updateStatus(String report_id, String report_status) {
		int rowNumber = jdbc.update(SQL_UPDATE_REPORT_STATUS,
				report_id, report_status);
		return rowNumber ;
	}
	
	
	/**
	 * TaskテーブルからユーザIDをキーにデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void tasklistCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		TaskRowCallbackHandler handler = new TaskRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL, handler);
	}

}
