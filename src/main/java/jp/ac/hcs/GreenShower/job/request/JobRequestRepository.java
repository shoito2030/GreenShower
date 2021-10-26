package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import jp.ac.hcs.GreenShower.job.request.JobRequestData.Content;

@Repository
public class JobRequestRepository {
	

	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM REQUESTS;";
	
	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL_REQUESTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID;";
	
	/* ユーザIDからクラス・番号・名前を取得するSQL */
	private static final String SQL_SELECT_PERSONAL_INFO ="SELECT NAME,CLASSROOM,CLASS_NUMBER  FROM USERS WHERE USER_ID = ?;";

	/* 特定のユーザ一人分の申請情報取得 */
	private static final String SQL_SELECT_STUDENT_REQUESTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID AND JH.APPLICANT_ID = ?;";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	/**
	 * TaskテーブルからユーザIDをキーにデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void requestlistCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		JobRequestRowCallbackHandler handler = new JobRequestRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL, handler);
	}

	/**
	 * job_huntingテーブルとrequestsテーブルに問い合わせ申請情報を全件取得する
	 * @return  jobRequestEntity
	 * @throws DataAccessException
	 */
	public JobRequestEntity selectAllRequests() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REQUESTS);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}
	
	/**
	 * job_huntingテーブルとrequestsテーブルから取得したデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param resultList report job_huntingテーブルとrequestsテーブルから取得したデータ
	 * @return JobRequestEntity
	 */
	private JobRequestEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		JobRequestEntity entity = new JobRequestEntity();
		
		for (Map<String, Object> map : resultList) {
			JobRequestData data = new JobRequestData();

			// JobHuntingDataクラスのフィールドを補完（protectedなフィールド）
			data.setClassroom((String) map.get("classroom"));
			data.setClass_number((String) map.get("class_number"));
			data.setName((String) map.get("name"));
			data.setApply_id((String) map.get("apply_id"));
			data.setApplicant_id((String) map.get("applicant_id"));
			data.setStatus(CommonEnum.getEnum(JobHuntingData.Status.class,(String) map.get("status")));
			data.setApply_type(CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) map.get("apply_type")));
			data.setIndicate((String) map.get("indicate"));
			
			
			// JobRequestDataクラスのフィールドを補完
			data.setDate_activity_from((Date) map.get("date_activity_from"));
			data.setDate_activity_to((Date) map.get("date_activity_to"));
			data.setLoc((String) map.get("loc"));
			data.setContent(CommonEnum.getEnum(Content.class, (String) map.get("content")));
			data.setCompany_name((String) map.get("company_name"));
//			data.setMeans((String) map.get("means"));
			data.setDate_absence_from((Date) map.get("date_absence_from"));
			data.setDate_absence_to((Date) map.get("date_absence_to"));
			data.setLeave_early_date((Date) map.get("leave_early_date"));
			data.setAttendance_date((Date) map.get("attendance_date"));
			data.setRemark((String) map.get("remark"));
			
			
			// システム内で使用しないので取得する必要ないかも（応相談）
//			data.setRegister_date((Date) map.get("register_date"));
//			data.setRegister_user_id((String) map.get("register_user_id"));
//			data.setUpdate_date((Date) map.get("update_date"));
//			data.setUpdate_user_id((String) map.get("update_user_id"));
			entity.getJobRequestList().add(data);
		}
		
		return entity;

	}

	public JobRequestEntity selectStudentRequests(String name) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_STUDENT_REQUESTS, name);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}
}
