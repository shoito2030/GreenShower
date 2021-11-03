package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Apply_type;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Status;
import jp.ac.hcs.GreenShower.job.request.JobRequestData.Way;

@Repository
public class JobRequestRepository {

	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL_REQUESTS = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS  FROM JOB_HUNTING JH\r\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID\r\n"
			+ "WHERE NOT EXISTS(SELECT *  FROM REPORTS REP WHERE JH.APPLY_ID  = REP.APPLY_ID) ORDER BY JH.STATUS DESC;";

	/** SQL 申請情報1件取得 */
	private static final String SQL_SELECT_REQUEST = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REQ.DATE_ACTIVITY_FROM, REQ.DATE_ACTIVITY_TO, REQ.LOC, REQ.WAY, REQ.DATE_ABSENCE_FROM, REQ.DATE_ABSENCE_TO, REQ.LEAVE_EARLY_DATE, REQ.ATTENDANCE_DATE, REQ.REMARK \n"
			+ "FROM JOB_HUNTING JH\n" + "LEFT JOIN REQUESTS REQ ON REQ.APPLY_ID  =  JH.APPLY_ID\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \n"
			+ "WHERE JH.APPLY_ID = ?;";
	
	/** 申請一件追加 */
	private static final String SQL_INSERT_JOB_HUNTING = "INSERT INTO JOB_HUNTING(APPLY_ID, APPLICANT_ID, CONTENT, COMPANY_NAME, APPLY_TYPE) VALUES(?, ?, ?, ?, ?);";
	private static final String SQL_INSERT_JOB_REQUESTS = "INSERT INTO REQUESTS(APPLY_ID, DATE_ACTIVITY_FROM, DATE_ACTIVITY_TO, LOC, WAY, DATE_ABSENCE_FROM, DATE_ABSENCE_TO, LEAVE_EARLY_DATE, ATTENDANCE_DATE, REMARK, REGISTER_USER_ID) VALUES"
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SQL_UPDATE_JOBSTATUS = "UPDATE job_hunting SET status=?,indicate=? WHERE apply_id=?";

	private static final String SQL_MAX_APPLY_ID = "SELECT MAX(apply_id) FROM JOB_HUNTING";
	
	private static final String SQL_SEARCH_USERID = "SELECT USER_ID FROM USERS WHERE CLASSROOM = ? AND CLASS_NUMBER = ?";

	@Autowired
	private JdbcTemplate jdbc;

	/**
	 * [V103]就職活動申請一覧画面_教師を表示するために必要なデータを取得する
	 * 
	 * @return jobRequestEntity
	 * @throws DataAccessException
	 */
	public JobRequestEntity selectAllRequests() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REQUESTS);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}
	
	/**
	 * 申請IDから1件の申請情報を取得する
	 * @param apply_id 申請ID
	 * @return JobRequestData
	 */
	public JobRequestData selectOne(String apply_id) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_REQUEST, apply_id);
		JobRequestData jobRequestData = mappingSelectResult(result);

		return jobRequestData;
	}

	
	/**
	 * job_huntingテーブルとrequestsテーブルから取得したデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param result job_huntingテーブルとrequestsテーブルから取得したデータ
	 * @return JobRequestData
	 */
	private JobRequestData mappingSelectResult(Map<String, Object> result) {
		JobRequestData data = new JobRequestData();

		// JobHuntingDataクラスのフィールドを補完（protectedなフィールド）
		data.setClassroom((String) result.get("classroom"));
		data.setClass_number((String) result.get("class_number"));
		data.setName((String) result.get("name"));
		data.setApply_id((String) result.get("apply_id"));
		data.setApplicant_id((String) result.get("applicant_id"));
		data.setContent(CommonEnum.getEnum(Content.class, (String) result.get("content")));
		data.setCompany_name((String) result.get("company_name"));
		data.setStatus(CommonEnum.getEnum(Status.class, (String) result.get("status")));
		data.setApply_type(CommonEnum.getEnum(Apply_type.class, (String) result.get("apply_type")));
		data.setIndicate((String) result.get("indicate"));

		// JobRequestDataクラスのフィールドを補完
		data.setWay(CommonEnum.getEnum(Way.class, (String) result.get("way")));
		data.setDate_activity_from((Date) result.get("date_activity_from"));
		data.setDate_activity_to((Date) result.get("date_activity_to"));
		data.setLoc((String) result.get("loc"));
		data.setDate_absence_from((Date) result.get("date_absence_from"));
		data.setDate_absence_to((Date) result.get("date_absence_to"));
		data.setLeave_early_date((Date) result.get("leave_early_date"));
		data.setAttendance_date((Date) result.get("attendance_date"));
		data.setRemark((String) result.get("remark"));
		
		return data;
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
			data.setStatus(CommonEnum.getEnum(Status.class, (String) map.get("status")));
			data.setApply_type(CommonEnum.getEnum(Apply_type.class, (String) map.get("apply_type")));
			data.setIndicate((String) map.get("indicate"));

			// JobRequestDataクラスのフィールドを補完
			data.setWay(CommonEnum.getEnum(Way.class, (String) map.get("way")));
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

	public int insertOne(JobRequestData data) {
		int rowNumber = jdbc.update(SQL_INSERT_JOB_HUNTING, data.getApply_id(), data.getApplicant_id(),
				data.getContent().getId(), data.getCompany_name(), data.getApply_type().getId());

		jdbc.update(SQL_INSERT_JOB_REQUESTS, data.getApply_id(), data.getDate_activity_from(),
				data.getDate_activity_to(), data.getLoc(), data.getWay().getId(), data.getDate_absence_from(),
				data.getDate_absence_to(), data.getLeave_early_date(), data.getAttendance_date(), data.getRemark(),
				data.getRegister_user_id());
		return rowNumber;
	}

	public int apply_id_get() {
		int apply_id = jdbc.queryForObject(SQL_MAX_APPLY_ID, int.class);
		return apply_id;

	}

	public int updateJobStatus(String apply_id, JobRequestForm form) {
		int rowNumber = jdbc.update(SQL_UPDATE_JOBSTATUS, form.getStatus(), form.getIndicate(), apply_id);
		return rowNumber;
	}

	public String searchUserId(String classi, String number) {
		String userId = jdbc.queryForObject(SQL_SEARCH_USERID, String.class, classi, number);
		return userId;
	}

	

}