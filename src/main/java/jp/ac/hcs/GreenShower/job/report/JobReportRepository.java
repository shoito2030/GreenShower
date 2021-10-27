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

@Repository
public class JobReportRepository {
	
	/** SQL 報告情報全件取得 */
	private static final String SQL_SELECT_ALL_REPORTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, REPORTS REP, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLY_ID = REP.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID ORDER BY JH.STATUS;";
	
	/** 特定のユーザ一人分の報告情報取得 */
	private static final String SQL_SELECT_STUDENT_REPORTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, REPORTS REP, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLY_ID = REP.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID AND JH.APPLICANT_ID = ? ORDER BY JH.STATUS;";
	
	/** SQL 報告情報一件追加 */
	private static final String SQL_INSERT_ONE_REPORTS = "INSERT INTO reports (APPLY_ID, ADVANCE_OR_RETREAT, REMARK, REGISTER_DATE, REGISTER_USER_ID, UPDATE_DATE, UPDATE_USER_ID) VALUES((SELECT MAX(apply_id) + 1 FROM reports),?,?,?,?,?,?);";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	public JobReportEntity selectAllReports() {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_REPORTS);
		JobReportEntity jobReportEntity = mappingSelectResult(resultList);

		return jobReportEntity;
	}

	public JobReportEntity selectStudentReports(String name) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_STUDENT_REPORTS, name);
		JobReportEntity jobReportEntity = mappingSelectResult(resultList);

		return jobReportEntity;
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
				data.getRegister_date(),
				data.getRegister_user_id(),
				data.getUpdate_date(),
				data.getUpdate_user_id());
		
		return rowNumber;
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
			
			// システム内で使用しないので取得する必要ないかも（応相談）
//			data.setRegister_date((Date) map.get("register_date"));
//			data.setRegister_user_id((String) map.get("register_user_id"));
//			data.setUpdate_date((Date) map.get("update_date"));
//			data.setUpdate_user_id((String) map.get("update_user_id"));
			entity.getJobReportList().add(data);
		}
		
		return entity;

	}

}
