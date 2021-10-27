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
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;

@Repository
public class JobRequestRepository {
	

	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM REQUESTS;";
	
	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL_REQUESTS = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REQ.DATE_ACTIVITY_FROM, REQ.DATE_ACTIVITY_TO, REQ.LOC,  REQ.DATE_ABSENCE_FROM, REQ.DATE_ABSENCE_TO, REQ.LEAVE_EARLY_DATE, REQ.ATTENDANCE_DATE, REQ.REMARK \n"
			+ "FROM JOB_HUNTING JH\n"
			+ "LEFT JOIN REQUESTS REQ ON REQ.APPLY_ID  =  JH.APPLY_ID\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \n"
			+ "WHERE NOT EXISTS(SELECT *  FROM REPORTS REP2 WHERE JH.APPLY_ID  = REP2.APPLY_ID) ORDER BY JH.STATUS, U.CLASSROOM, U.CLASS_NUMBER;";
	
	/* ユーザIDからクラス・番号・名前を取得するSQL */
	private static final String SQL_SELECT_PERSONAL_INFO ="SELECT NAME,CLASSROOM,CLASS_NUMBER  FROM USERS WHERE USER_ID = ?;";

	/* 特定のユーザ一人分の申請情報取得 */
	private static final String SQL_SELECT_STUDENT_REQUESTS = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REQ.DATE_ACTIVITY_FROM, REQ.DATE_ACTIVITY_TO, REQ.LOC,  REQ.DATE_ABSENCE_FROM, REQ.DATE_ABSENCE_TO, REQ.LEAVE_EARLY_DATE, REQ.ATTENDANCE_DATE, REQ.REMARK \n"
			+ "FROM JOB_HUNTING JH\n"
			+ "LEFT JOIN REQUESTS REQ ON REQ.APPLY_ID  =  JH.APPLY_ID\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \n"
			+ "WHERE NOT EXISTS(SELECT *  FROM REPORTS REP2 WHERE JH.APPLY_ID  = REP2.APPLY_ID) AND U.USER_ID = ? ORDER BY JH.STATUS, U.CLASSROOM, U.CLASS_NUMBER;";
	
	@Autowired
	private JdbcTemplate jdbc;
	

	/**
	 * [V103]就職活動申請一覧画面_教師を表示するために必要なデータを取得する
	 * @return  jobRequestEntity
	 * @throws DataAccessException
	 */
	public JobRequestEntity selectAllRequests() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REQUESTS);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}
	
	/**
	 * [V103]就職活動申請一覧画面_教師を表示するために必要なデータを取得する
	 * @return  jobRequestEntity
	 * @throws DataAccessException
	 */
	public JobRequestEntity selectStudentRequests(String user_id) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_STUDENT_REQUESTS, user_id);
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
			data.setContent(CommonEnum.getEnum(Content.class, (String) map.get("content")));
			data.setCompany_name((String) map.get("company_name"));
			data.setStatus(CommonEnum.getEnum(JobHuntingData.Status.class,(String) map.get("status")));
			data.setApply_type(CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) map.get("apply_type")));
			data.setIndicate((String) map.get("indicate"));
			
			// JobRequestDataクラスのフィールドを補完
			data.setDate_activity_from((Date) map.get("date_activity_from"));
			data.setDate_activity_to((Date) map.get("date_activity_to"));
			data.setLoc((String) map.get("loc"));
			data.setDate_absence_from((Date) map.get("date_absence_from"));
			data.setDate_absence_to((Date) map.get("date_absence_to"));
			data.setLeave_early_date((Date) map.get("leave_early_date"));
			data.setAttendance_date((Date) map.get("attendance_date"));
			data.setRemark((String) map.get("remark"));
			
			entity.getJobRequestList().add(data);
		}
		
		return entity;

	}
}