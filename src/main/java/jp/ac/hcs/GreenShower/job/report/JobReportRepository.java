package jp.ac.hcs.GreenShower.job.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;
import jp.ac.hcs.GreenShower.user.UserData;

@Repository
public class JobReportRepository {

	/** SQL 報告情報全件取得 */
	private static final String SQL_SELECT_ALL_REPORTS = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLY_ID, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS  FROM JOB_HUNTING \r\n"
			+ "JH LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \r\n"
			+ "WHERE JH.STATUS > '4' ORDER BY JH.STATUS DESC;";

	/* 特定の1件の報告情報を取得 */
	private static final String SQL_SELECT_ONE = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLICANT_ID, JH.APPLY_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REP.ADVANCE_OR_RETREAT, REP.REMARK\r\n"
			+ "FROM JOB_HUNTING JH LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \r\n"
			+ "LEFT JOIN REPORTS REP ON REP.APPLY_ID = JH.APPLY_ID WHERE JH.APPLY_ID  = ?;";

	/** SQL 報告情報一件追加 */
	private static final String SQL_INSERT_ONE_REPORTS = "INSERT INTO REPORTS(APPLY_ID, ADVANCE_OR_RETREAT, REMARK, REGISTER_USER_ID) VALUES(?, ?, ?, ?);";

	/** 報告情報状態更新(insert用) */
	private static final String SQL_UPDATE_JOB_HUNTING = "UPDATE JOB_HUNTING SET STATUS = '6' WHERE APPLY_ID = ?;";

	/** 報告情報状態更新(状態変更用) */
	private static final String SQL_UPDATE_JOBSTATUS = "UPDATE JOB_HUNTING SET STATUS = ?, INDICATE = ? WHERE APPLY_ID = ?;";

	/** 申請IDからクラス・番号・名前を取得するSQL */
	private static final String SQL_SELECT_PERSONAL_INFO = "SELECT NAME,CLASSROOM,CLASS_NUMBER  FROM USERS U LEFT JOIN JOB_HUNTING JH ON U.USER_ID = JH.APPLICANT_ID WHERE JH.APPLY_ID = ?;";

	/** 報告情報内容更新（進退用） */
	private static final String SQL_UPDATE_ADVANCE_OR_RETREAT_TO_TRUE = "UPDATE REPORTS  SET ADVANCE_OR_RETREAT  = TRUE WHERE APPLY_ID = ?;";

	/** 報告情報内容更新（進退用） */
	private static final String SQL_UPDATE_ADVANCE_OR_RETREAT_TO_FALSE = "UPDATE REPORTS  SET ADVANCE_OR_RETREAT  = FALSE WHERE APPLY_ID = ?;";
	
	/** 報告情報内容更新（メモ用） */
	private static final String SQL_UPDATE_REMARK = "UPDATE REPORTS  SET REMARK = ? WHERE APPLY_ID = ?;";
	
	@Autowired
	private JdbcTemplate jdbc;

	public JobReportEntity selectAllReports() {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REPORTS);
		JobReportEntity jobReportEntity = mappingSelectResult(resultList);

		return jobReportEntity;
	}

	/**
	 * 特定の1件を取得
	 * 
	 * @param apply_id 申請ID
	 * @return JobHuntingData 1件の報告情報
	 */
	public JobReportData selectOne(String apply_id) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_ONE, apply_id);
		JobReportData jobReportData = mappingSelectResult(result);
		
		return jobReportData;
	}

	/**
	 * 特定のユーザ一人の情報を取得
	 * 
	 * @param apply_id ユーザID
	 * @return UserData ユーザ情報
	 */
	public UserData selectPersonalInfo(String apply_id) {
		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_PERSONAL_INFO, apply_id);
		UserData data = mappingSelectPersonalInfoResult(result);
		return data;
	}

	/**
	 * reportsテーブルにデータを1件追加する.
	 * 
	 * @param data 追加する報告情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(JobReportData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE_REPORTS,

				data.getApply_id(), data.isAdvance_or_retreat(), data.getRemark(), data.getRegister_user_id());

		return rowNumber;
	}

	/**
	 * reportsテーブルの進退を『進めない』に変更する
	 * 
	 * @param apply_id
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int updateAdvance_or_retreat_to_false(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_ADVANCE_OR_RETREAT_TO_FALSE, apply_id);
		return rowNumber;
	}

	/**
	 * reportsテーブルの進退を『進める』に変更する
	 * 
	 * @param apply_id
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int updateAdvance_or_retreat_to_true(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_ADVANCE_OR_RETREAT_TO_TRUE, apply_id);
		return rowNumber;
	}

	/**
	 * reportsテーブルのメモを変更する
	 * 
	 * @param apply_id
	 * @param remark
	 * @return 修正データ数
	 * @throws DataAccessException
	 */
	public int updateRemark(String apply_id, String remark) {
		int rowNumber = jdbc.update(SQL_UPDATE_REMARK, remark, apply_id);
		return rowNumber;
	}

	/**
	 * job_huntingテーブルの状態を6:報告承認待に変更する
	 * 
	 * @param apply_id 申請ID
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int updateStatusOne(String apply_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_JOB_HUNTING, apply_id);

		return rowNumber;
	}

	/**
	 * job_huntingテーブルの状態を任意の状態に更新する
	 * 
	 * @param form 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int updateStatus(JobReportForm form) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_JOBSTATUS,

				form.getStatus(), form.getIndicate(), form.getApply_id());

		return rowNumber;
	}

	/**
	 * usersテーブルから取得したデータをUserData形式にマッピングする.
	 * 
	 * @param result report usersテーブルから取得したデータ
	 * @return UserData
	 */
	private UserData mappingSelectPersonalInfoResult(Map<String, Object> result) {
		UserData data = new UserData();
		data.setClass_number((String) result.get("class_number"));
		data.setClassroom((String) result.get("classroom"));
		data.setName((String) result.get("name"));

		return data;
	}
	
	
	/**
	 * job_huntingテーブルとreportsテーブルから取得したデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param result  job_huntingテーブルとreportsテーブルから取得したデータ
	 * @return JobRequestEntity
	 */
	private JobReportData mappingSelectResult(Map<String, Object> result) {
		JobReportData data = new JobReportData();

		// JobHuntingDataクラスのフィールドを補完（protectedなフィールド）
		data.setCompany_name((String) result.get("company_name"));
		data.setContent(CommonEnum.getEnum(Content.class, (String) result.get("content")));
		data.setClassroom((String) result.get("classroom"));
		data.setClass_number((String) result.get("class_number"));
		data.setName((String) result.get("name"));
		data.setApply_id((String) result.get("apply_id"));
		data.setApplicant_id((String) result.get("applicant_id"));
		data.setStatus(CommonEnum.getEnum(JobHuntingData.Status.class, (String) result.get("status")));
		data.setApply_type(CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) result.get("apply_type")));
		data.setIndicate((String) result.get("indicate"));

		// JobReportDataクラスのフィールドを補完
		data.setRemark((String) result.get("remark"));
		data.setAdvance_or_retreat((boolean) result.get("advance_or_retreat"));
		return data;
	}

	/**
	 *  job_huntingテーブルとreportsテーブルから取得したデータをJobRequsettEntity形式にマッピングする.
	 * 
	 * @param resultList report job_huntingテーブルとrequestsテーブルから取得したデータ
	 * @return JobRequestEntity
	 */
	private JobReportEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		JobReportEntity entity = new JobReportEntity();

		for (Map<String, Object> map : resultList) {
			JobReportData data = new JobReportData();

			// JobHuntingDataクラスのフィールドを補完（protectedなフィールド）
			data.setCompany_name((String) map.get("company_name"));
			data.setContent(CommonEnum.getEnum(Content.class, (String) map.get("content")));
			data.setClassroom((String) map.get("classroom"));
			data.setClass_number((String) map.get("class_number"));
			data.setName((String) map.get("name"));
			data.setApply_id((String) map.get("apply_id"));
			data.setApplicant_id((String) map.get("applicant_id"));
			data.setStatus(CommonEnum.getEnum(JobHuntingData.Status.class, (String) map.get("status")));
			data.setApply_type(CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) map.get("apply_type")));

			entity.getJobReportList().add(data);
		}

		return entity;

	}

}
