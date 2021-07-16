package jp.ac.hcs.GreenShower.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.user.UserData;

@Repository
public class ReportRepository {

	/** SQL 全件取得（クラスと出席番号の昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM report order by classroom, class_number";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM report WHERE user_id = ?";
	
	
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	/**
	 * Userテーブルから全データを取得.
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
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserData selectOne(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, user_id);
		ReportEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		UserData data = entity.getReportlist().get(0);
		return data;
	}
	
	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * 
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private ReportEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		ReportEntity entity = new ReportEntity();

		for (Map<String, Object> map : resultList) {
			ReportData data = new ReportData();
			
			data.setReport_id((String) map.get("report_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setClassroom((String) map.get("classroom"));
			data.setClass_number((String) map.get("class_number"));
			data.setName((String) map.get("name"));
			data.setCourse_code((String) map.get("course_code"));
			data.setCompany_name((String) map.get("company_name"));
			data.setCompany_name_kana((String) map.get("company_name_kana"));
			data.setDatetime((Date) map.get("datetime"));
			data.setPlace((String) map.get("place"));
			data.setEntry_section((int) map.get("entry_section"));
			data.setEntry_section_other((String) map.get("entry_section_other"));
			data.setVenue_section((int) map.get("venue_section"));
			data.setVenue_section_other((String) map.get("venue_section_other"));
			data.setTest_section((int) map.get("test_section"));
			data.setTest_section_other((String) map.get("test_section_other"));
			data.setTest_summary((int) map.get("test_summary"));
			data.setTest_summary_other((String) map.get("test_summary_other"));
			data.setResult_notification((int) map.get("result_notification"));
			data.setAptitude_test_detail((int) map.get("aptitude_test_detail"));
			data.setAptitude_test_detail_other((String) map.get("aptitude_test_detail_other"));
			data.setInterview_detail((int) map.get("interview_detail"));
			data.setInterview_detail_other((String) map.get("interview_detail_other"));
			data.setInterview_number((int) map.get("interview_number"));
			data.setInterviewer_number((int) map.get("interviewer_number"));
			data.setInterviewer_position((String) map.get("interviewer_position"));
			data.setInterview_time((int) map.get("interview_time"));
			data.setTheme((String) map.get("itheme"));
			data.setQuestion_contents((String) map.get("question_contents"));
			data.setReport_status((int) map.get("report_status"));
			data.setRegistered_date((Date) map.get("request_date"));
			data.setRegistered_user_id((String) map.get("registered_user_id"));
			data.setRemarks((String) map.get("remarks"));

			entity.getReportlist().add(data);
		}
		return entity;
	}

}
