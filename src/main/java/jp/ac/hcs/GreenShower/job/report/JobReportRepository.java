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
	private static final String SQL_SELECT_ALL_REPORTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, REPORTS REP, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLY_ID = REP.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID ORDER BY JH.STATUS;";
	
	/* 特定の1件の報告情報を取得 */
	private static final String SQL_SELECT_ONE = "SELECT U.NAME, U.CLASSROOM, U.CLASS_NUMBER, JH.APPLICANT_ID, JH.CONTENT, JH.COMPANY_NAME, JH.STATUS, JH.APPLY_TYPE, JH.INDICATE, REP.ADVANCE_OR_RETREAT, REP.REMARK\r\n"
			+ "FROM JOB_HUNTING JH\r\n"
			+ "LEFT JOIN USERS U ON  U.USER_ID  =  JH.APPLICANT_ID \r\n"
			+ "LEFT JOIN REPORTS REP ON REP.APPLY_ID = JH.APPLY_ID \r\n"
			+ "WHERE JH.APPLY_ID  = ?\r\n"
			+ "LIMIT 1;";
	
	/** SQL 報告情報一件追加 */
	private static final String SQL_INSERT_ONE_REPORTS = "INSERT INTO REPORTS(APPLY_ID, ADVANCE_OR_RETREAT, REMARK, REGISTER_USER_ID) VALUES(?, ?, ?, ?);";
	
	/** 報告情報状態更新 */
	private static final String SQL_UPDATE_JOB_HUNTING = "UPDATE JOB_HUNTING SET STATUS = '6' WHERE APPLY_ID = ?;";
	
	/** ユーザIDからクラス・番号・名前を取得するSQL */
	private static final String SQL_SELECT_PERSONAL_INFO ="SELECT NAME,CLASSROOM,CLASS_NUMBER  FROM USERS U LEFT JOIN JOB_HUNTING JH ON U.USER_ID = JH.APPLICANT_ID WHERE JH.APPLY_ID = ?;";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	public JobReportEntity selectAllReports() {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REPORTS);
		JobReportEntity jobReportEntity = mappingSelectResult(resultList);

		return jobReportEntity;
	}
	
	/**
	 * 特定の1件を取得
	 * @param apply_id 申請ID
	 * @return JobHuntingData 1件の報告情報
	 */
	public JobHuntingData selectOne(String apply_id) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, apply_id);
		JobReportEntity jobReportEntity = mappingSelectResult(resultList);
		return jobReportEntity.getJobReportList().get(0);
	}
	
	
	/**
	 * 特定のユーザ一人の情報を取得
	 * @param user_id ユーザID
	 * @return UserData ユーザ情報
	 */
	public UserData selectPersonalInfo(String apply_id) {
		Map<String, Object> result = jdbc.queryForList(SQL_SELECT_PERSONAL_INFO, apply_id).get(0);
		UserData data = mappingSelectResult(result);
		return data;
	}

	
	/**
	 * reportsテーブルにデータを1件追加する.
	 * 
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(JobReportData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE_REPORTS,

				data.getApply_id(),
				data.isAdvance_or_retreat(),
				data.getRemark(),
				data.getRegister_user_id());
		
		return rowNumber;
	}
	
	/**
	 * job_huntingテーブルの状態を更新する
	 * 
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int updateStatusOne(String register_user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_JOB_HUNTING,register_user_id);
		
		return rowNumber;
	}
	
	
	/**
	 * usersテーブルから取得したデータをUserData形式にマッピングする.
	 * 
	 * @param result report usersテーブルから取得したデータ
	 * @return UserData
	 */
	private UserData mappingSelectResult(Map<String, Object> result) {
		UserData data = new UserData();
		data.setClass_number((String)result.get("class_number"));
		data.setClassroom((String)result.get("classroom"));
		data.setName((String)result.get("name"));
		
		return data;
	}
	
	/**
	 * job_huntingテーブルとrequestsテーブルから取得したデータをJobRequsettEntity形式にマッピングする.
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
			data.setStatus(CommonEnum.getEnum(JobHuntingData.Status.class,(String) map.get("status")));
			data.setApply_type(CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) map.get("apply_type")));
			data.setIndicate((String) map.get("indicate"));
			
			
			// JobReportDataクラスのフィールドを補完
			data.setRemark((String) map.get("remark"));
			data.setAdvance_or_retreat((boolean) map.get("advance_or_retreat"));
			
			entity.getJobReportList().add(data);
		}
		
		return entity;

	}

}
