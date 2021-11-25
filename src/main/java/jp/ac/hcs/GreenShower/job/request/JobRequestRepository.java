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
import jp.ac.hcs.GreenShower.user.UserData;

/**
 * 就職活動申請に関する処理を行うRepositoryクラス
 *
 */
@Repository
public class JobRequestRepository {

	/** 申請情報を全件取得（ユーザの所属クラスのもののみ）するSQL */
	private static final String SQL_SELECT_ALL_REQUESTS = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS  FROM JOB_HUNTING JH\r\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID\r\n"
			+ "WHERE NOT EXISTS(SELECT *  FROM REPORTS REP WHERE JH.APPLY_ID  = REP.APPLY_ID) AND U.CLASSROOM = ? ORDER BY JH.STATUS DESC;";
	
	/** 申請情報を全件取得(報告済みも含む)*/
	private static final String SQL_SELECT_ALL_NOTFICATION = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS  FROM JOB_HUNTING JH\r\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID\r\n"
			+ "WHERE U.CLASSROOM = ? ORDER BY JH.STATUS DESC;";
	/** ユーザIDからクラス・番号・名前を取得するSQL */
	private static final String SQL_SELECT_PERSONAL_INFO = "SELECT NAME,CLASSROOM,CLASS_NUMBER  FROM USERS WHERE USER_ID  = ?;";
	
	/** クラス・番号からユーザ情報を取得するSQL (API用)*/
	private static final String SQL_SELECT_PERSONAL_INFO_FOR_API = "SELECT USER_ID, NAME,CLASSROOM,CLASS_NUMBER  FROM USERS WHERE CLASSROOM = ? AND CLASS_NUMBER = ?;";

	/** ユーザが所属するクラスの人数を取得するSQL */
	private static final String SQL_SELECT_STUDENTS_NUMBER = "SELECT COUNT(U1.USER_ID) AS STUDENTS_NUMBER FROM USERS U1 WHERE U1.CLASSROOM = (SELECT U2.CLASSROOM FROM USERS U2 WHERE U2.USER_ID = ?) AND U1.ROLE = 'ROLE_STUDENT';";

	/** 申請情報を1件取得するSQL */
	private static final String SQL_SELECT_REQUEST = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REQ.DATE_ACTIVITY_FROM, REQ.DATE_ACTIVITY_TO, REQ.LOC, REQ.WAY, REQ.DATE_ABSENCE_FROM, REQ.DATE_ABSENCE_TO, REQ.LEAVE_EARLY_DATE, REQ.ATTENDANCE_DATE, REQ.REMARK \n"
			+ "FROM JOB_HUNTING JH\n" + "LEFT JOIN REQUESTS REQ ON REQ.APPLY_ID  =  JH.APPLY_ID\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \n" + "WHERE JH.APPLY_ID = ?;";

	/** 就職活動申請マスタに申請情報を追加するSQL*/
	private static final String SQL_INSERT_JOB_HUNTING = "INSERT INTO JOB_HUNTING(APPLY_ID, APPLICANT_ID, CONTENT, COMPANY_NAME, APPLY_TYPE) VALUES(?, ?, ?, ?, ?);";
	
	/** 申請マスタに申請情報を追加するSQL */
	private static final String SQL_INSERT_JOB_REQUESTS = "INSERT INTO REQUESTS(APPLY_ID, DATE_ACTIVITY_FROM, DATE_ACTIVITY_TO, LOC, WAY, DATE_ABSENCE_FROM, DATE_ABSENCE_TO, LEAVE_EARLY_DATE, ATTENDANCE_DATE, REMARK, REGISTER_USER_ID) VALUES"
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** 申請状態を変更するSQL */
	private static final String SQL_UPDATE_JOBSTATUS = "UPDATE JOB_HUNTING SET STATUS=?,INDICATE=? WHERE APPLY_ID=?";
	
	/** 申請状態を『申請承認済み』に変更するSQL */
	private static final String SQL_NOTICE_COURSE_DIRECTOR = "UPDATE JOB_HUNTING SET STATUS='4' WHERE APPLY_ID=?";
	
	/** 申請内容を変更するSQL */
	private static final String SQL_UPDATE_JOBCONTENT_REQUESTS = "UPDATE requests SET date_activity_from=?,date_activity_to=?,loc=?,way=?,date_absence_from=?,date_absence_to=?,leave_early_date=?,attendance_date=?,remark=? WHERE apply_id=?";
	
	/** 申請マスタの情報を更新するSQL */
	private static final String SQL_UPDATE_JOBCONTENT_JOB_HUNTING = "UPDATE job_hunting SET apply_type=?,company_name=?,content=? WHERE apply_id=?";
	
	/** 申請マスタから最新の申請IDを取得するSQL */
	private static final String SQL_MAX_APPLY_ID = "SELECT MAX(CAST(APPLY_ID AS INT)) AS APPLY_ID FROM JOB_HUNTING";
	
	/** イベントマスタから最新のイベントIDを取得するSQL */
	private static final String SQL_MAX_EVENT_ID = "SELECT MAX(CAST(event_id AS INT)) AS EVENT_ID FROM events";

	/** イベントマスタの情報を更新するSQL */
	private static final String SQL_UPDATE_JOBEVENT = "INSERT INTO EVENTS(event_id, company_name, datetime, loc, content, bring, register_user_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
	
	/** 就職活動申請マスタの申請状態を取得するSQL*/
	private static final String SQL_SELECT_JOB_HUNTING_STATUS = "SELECT JH.STATUS  FROM JOB_HUNTING JH WHERE JH.APPLY_ID = ?;";
	
	/** 申請承認待ちに変更する*/
	private static final String SQL_UPDATE_JOB_HUNTING = "UPDATE JOB_HUNTING SET STATUS = '2' WHERE APPLY_ID = ?;";
	@Autowired
	private JdbcTemplate jdbc;


	/**
	 * 申請情報を全件取得（ユーザの所属クラスのもののみ）する
	 * 
	 * @param classroom
	 * @return jobRequestEntity
	 * @throws DataAccessException
	 */
	public JobRequestEntity selectAllRequests(String classroom) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REQUESTS, classroom);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}

	/**
	 * 1件の申請情報を取得する
	 * 
	 * @param apply_id 申請ID
	 * @return JobRequestData
	 * @throws DataAccessException
	 */
	public JobRequestData selectOne(String apply_id) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_REQUEST, apply_id);
		JobRequestData jobRequestData = mappingSelectResult(result);

		return jobRequestData;
	}

	/**
	 * 特定のユーザ一人の情報を取得
	 * 
	 * @param user_id ユーザID
	 * @return UserData 
	 * @throws DataAccessException
	 */
	public UserData selectPersonalInfo(String user_id) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_PERSONAL_INFO, user_id);
		UserData data = mappingSelectPersonalInfoResult(result);
		return data;
	}

	/**
	 * 特定のユーザ一人の情報を取得（API用）
	 * @param classroom クラス
	 * @param class_number 出席番号
	 * @return data
	 * @throws DataAccessException
	 */
	public UserData selectPersonalInfo(String classroom, String class_number) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_PERSONAL_INFO_FOR_API, classroom, class_number);
		UserData data = mappingSelectPersonalInfoResult(result);
		return data;
	}

	/**
	 * 担任が受け持つ生徒数を取得する
	 * 
	 * @param user_id 担任のユーザID
	 * @return studentsNumber 生徒数
	 * @throws DataAccessException
	 */
	public int selectStudentsNumber(String user_id) {
		Map<String, Object> studentsNumber = jdbc.queryForMap(SQL_SELECT_STUDENTS_NUMBER, user_id);
		return Integer.valueOf(studentsNumber.get("STUDENTS_NUMBER").toString());
	}

	/**
	 * 受け取ったデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param result 問い合わせ結果
	 * @return JobRequestData
	 * @throws DataAccessException
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
	 * 受け取ったデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param resultList 問い合わせ結果
	 * @return JobRequestEntity
	 * @throws DataAccessException
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

	/**
	 * 受け取ったデータをUserData形式にマッピングする.
	 * 
	 * @param result 問い合わせ結果
	 * @return UserData
	 * @throws DataAccessException
	 */
	private UserData mappingSelectPersonalInfoResult(Map<String, Object> result) {
		UserData data = new UserData();		
		data.setUser_id((String) result.get("user_id"));
		data.setClass_number((String) result.get("class_number"));
		data.setClassroom((String) result.get("classroom"));
		data.setName((String) result.get("name"));

		return data;
	}

	/**
	 * 就職活動申請新規作成処理を実行する
	 * @param data 申請情報を格納したオブジェクト（JobRequestData）
	 * @return rowNumber 追加に成功した件数
	 * @throws DataAccessException
	 */
	public int insertOne(JobRequestData data) {
		int rowNumber = jdbc.update(SQL_INSERT_JOB_HUNTING, data.getApply_id(), data.getApplicant_id(),
				data.getContent().getId(), data.getCompany_name(), data.getApply_type().getId());

		jdbc.update(SQL_INSERT_JOB_REQUESTS, data.getApply_id(), data.getDate_activity_from(),
				data.getDate_activity_to(), data.getLoc(), data.getWay().getId(), data.getDate_absence_from(),
				data.getDate_absence_to(), data.getLeave_early_date(), data.getAttendance_date(), data.getRemark(),
				data.getRegister_user_id());
		return rowNumber;
	}

	/**
	 * 最新の申請IDを取得する
	 * @return apply_id
	 * @throws DataAccessException
	 */
	public int selectApply_id() {
		Map<String, Object> temp = jdbc.queryForMap(SQL_MAX_APPLY_ID);
		return (int) temp.get("apply_id");
	}

	/**
	 * 就職活動申請状態変更処理を実行する
	 * @param apply_id 申請ID
	 * @param status 申請状態
	 * @param indicate 指摘事項
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateJobStatus(String apply_id, String status, String indicate) {
		int rowNumber = jdbc.update(SQL_UPDATE_JOBSTATUS, status, indicate, apply_id);
		return rowNumber;
	}

	/**
	 * 就職活動申請内容変更処理を実行する
	 * @param data 申請情報を格納したオブジェクト(JobRequestData)
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateJobContent(JobRequestData data, String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_JOBCONTENT_REQUESTS, data.getDate_activity_from(),
				data.getDate_activity_to(), data.getLoc(), data.getWay().getId(), data.getDate_absence_from(),
				data.getDate_absence_to(), data.getLeave_early_date(), data.getAttendance_date(), data.getRemark(),
				apply_id);
		rowNumber = jdbc.update(SQL_UPDATE_JOBCONTENT_JOB_HUNTING, data.getApply_type().getId(), data.getCompany_name(),
				data.getContent().getId(), apply_id);
		return rowNumber;
	}
	
	/**
	 * 就職活動イベント登録処理を実行する
	 * @param eventData イベント情報を格納したオブジェクト
	 * @param user_id ユーザID
	 * @return rowNumber 登録に成功した件数
	 * @throws DataAccessException
	 */
	public int insertEvent(EventData eventData, String user_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_JOBEVENT,eventData.getEvent_id(), eventData.getCompany_name(), eventData.getDatetime(), eventData.getLoc(), eventData.getContent(), eventData.getBring(), user_id);
		return rowNumber;
	}

	/**
	 * 最新のイベントIDを取得する
	 * 
	 * @return event_id
	 * @throws DataAccessException
	 */
	public int selectEvent_id() {
		Map<String, Object> temp = jdbc.queryForMap(SQL_MAX_EVENT_ID);
		return (int)temp.get("event_id");
	}
	
	/**
	 * 申請IDに紐づく就職活動情報の状態を取得
	 * 
	 * @param apply_id 申請ID
	 * @return UserData ユーザ情報
	 * @throws DataAccessException
	 */
	public String selectJobHuntingStatus(String apply_id) {
		String status = (String) jdbc.queryForMap(SQL_SELECT_JOB_HUNTING_STATUS, apply_id).get("status");
		return status;
	}

	/**
	 * job_huntingテーブルの状態を2:申請承認待に変更する
	 * 
	 * @param apply_id 申請ID
	 * @return 変更データ数:0または1
	 * @throws DataAccessException
	 */
	public int updateStatusOne(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_JOB_HUNTING, apply_id);

		return rowNumber;
		
	}

	/**
	 * コース担当報告済登録機能を実行する
	 * @param apply_id 申請ID
	 * @return rowNumber 追加データ数:0または1
	 * @throws DataAccessException
	 */
	public int noticeCourseDirector(String apply_id) {
		int rowNumber = jdbc.update(SQL_NOTICE_COURSE_DIRECTOR, apply_id);

		return rowNumber;
	}

	public JobRequestEntity selectAllNotfication(String classroom) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_NOTFICATION, classroom);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);

		return jobRequestEntity;
	}
}