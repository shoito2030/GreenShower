package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.report.ReportRowCallbackHandler;

@Repository
public class JobRequestRepository {
	
	/** SQL 全件取得（クラスと出席番号の昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM JOB_HUNTING JOIN REQUESTS USING(APPLY_ID);";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	/**
	 * TaskテーブルからユーザIDをキーにデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void reportlistCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		ReportRowCallbackHandler handler = new ReportRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL, handler);
	}


	public JobRequestEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);
		return jobRequestEntity;
	}


	private JobRequestEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		JobRequestEntity entity = new JobRequestEntity();
		
		for (Map<String, Object> map : resultList) {
			JobRequestData data = new JobRequestData();
			
			data.setApply_id((String) map.get("apply_id"));
			data.setApplicant_id((String) map.get("applicant_id"));
			data.setStatus((String) map.get("status"));
			data.setApply_type((String) map.get("apply_type"));
			data.setIndicate((String) map.get("indicate"));
			data.setCompany_name((String) map.get("company_name"));
			data.setDate_activity_from((Date) map.get("date_activity_from"));
			data.setDate_activity_to((Date) map.get("date_activity_to"));
			data.setLoc((String) map.get("loc"));
			data.setMeans((String) map.get("means"));
			data.setRemark((String) map.get("remark"));
			data.setDate_absence_from((Date) map.get("date_absence_from"));
			data.setDate_absence_to((Date) map.get("date_absence_to"));
			data.setLeave_early_date((Date) map.get("leave_early_date"));
			data.setLeave_early_date((Date) map.get("leave_early_date"));
			data.setAttendance_date((Date) map.get("attendance_date"));
			data.setRegister_date((Date) map.get("register_date"));
			data.setRegister_user_id((String) map.get("register_user_id"));		
			data.setUpdate_date((Date) map.get("update_date"));
			data.setUpdate_user_id((String) map.get("update_user_id"));		}
		return null;
	}
}
