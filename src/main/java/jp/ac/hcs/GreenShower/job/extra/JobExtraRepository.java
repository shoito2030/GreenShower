package jp.ac.hcs.GreenShower.job.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobExtraRepository {
	
//	/** 申請IDに一致したとりまとめ書類マスタを一軒取得する */
//	private static final String SQL_SELECT_SUMMARY_DOCUMENTS = "SELECT * FROM SUMMARY_DOCUMENTS WHERE APPLY_ID = ?";
	/** 新たに学生をとりまとめ名簿に登録した日時をデータベースに登録する */
	private static final String SQL_INSERT_SUMMARY_LIST_MANAGEMENT = "INSERT INTO SUMMARY_LIST_MANAGEMENT(APPLY_ID,REGISTER_DATE,REGISTER_USER_ID) VALUES(?,CURRENT_TIMESTAMP,?)";
	/** 受け取った書類の日時をデータベースに登録する為に申請IDのみ登録する */
	private static final String SQL_INSERT_SUMMARY_DOCUMENTS = "INSERT INTO SUMMARY_DOCUMENTS(APPLY_ID) VALUES(?)";
	/** 申請IDをもとに受け取った各書類の日時をデータベースに登録する */
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_RESUME = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_RESUME = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_TRANRIPTSSCRIPTS = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_UNIVERSITY_TRANRIPTSSCRIPTS = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_DIPLOMA = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_UNIVERSITY_DIPLOMA = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_TRANSCRIPT = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_HCS_TRANSCRIPT = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_DIPLOMA = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_HCS_DIPLOMA = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_HEALTH_CERTIFICATE = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_HEALTH_CERTIFICATE = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_HIGH_SCHOOL_TRANSCRIPT = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_HIGH_SCHOOL_TRANSCRIPT = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_RECOMMENDATION = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_RECOMMENDATION = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_PERSONAL_INFORMATION_AGREEMENT = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_PERSONAL_INFORMATION_AGREEMENT = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	private static final String SQL_UPDATE_SUMMARY_DOCUMENTS_ALL_REQUIRED = "UPDATE SUMMARY_DOCUMENTS SET RECEIPT_OF_ALL_REQUIRED = CURRENT_TIMESTAMP WHERE APPLY_ID = ?";
	
	@Autowired
	private JdbcTemplate jdbc;
	
//	/**
//	 * 申請IDに一致したとりまとめ書類マスタを一件取得する
//	 * @param apply_id 申請ID
//	 * @return JobExtraData
//	 * @throws DataAccessException
//	 */
//	public JobExtraData selectSummaryDocuments(String apply_id) throws DataAccessException {
//		Map<String, Object> result = jdbc.queryForMap(SQL_SELECT_SUMMARY_DOCUMENTS, apply_id);
//		System.out.println(result);
//		JobExtraData jobExtraData = mappingSelectSummaryDocuments(result);
//
//		return jobExtraData;
//	}
	
//	/**
//	 * 受け取ったデータをJobExtraEntity形式にマッピングする
//	 * @param result
//	 * @return JobExtraData
//	 */
//	private JobExtraData mappingSelectSummaryDocuments(Map<String, Object> result) {
//		JobExtraData data = new JobExtraData();
//		
//		data.setApply_id((String)result.get("apply_id"));
//		data.setReceipt_of_resume(!result.get("receipt_of_resume").equals(null));
//		data.setReceipt_of_university_tranriptsscripts(!result.get("receipt_of_university_tranriptsscripts").equals(null));
//		data.setReceipt_of_university_diploma(!result.get("receipt_of_university_diploma").equals(null));
//		data.setReceipt_of_hcs_transcript(!result.get("receipt_of_hcs_transcript").equals(null));
//		data.setReceipt_of_hcs_diploma(!result.get("receipt_of_hcs_diploma").equals(null));
//		data.setReceipt_of_health_certificate(!result.get("receipt_of_health_certificate").equals(null));
//		data.setReceipt_of_high_school_transcript(!result.get("receipt_of_high_school_transcript").equals(null));
//		data.setReceipt_of_recommendation(!result.get("receipt_of_recommendation").equals(null));
//		data.setReceipt_of_personal_information_agreement(!result.get("receipt_of_personal_information_agreement").equals(null));
//		
//		return data;
//	}
	
	/**
	 * 新たに学生をとりまとめ名簿に登録した日時をデータベースに登録する
	 * @param apply_id 申請ID
	 * @param user_id  ユーザID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int listRegistion(String apply_id,String user_id) {
		int rowNumber = jdbc.update(SQL_INSERT_SUMMARY_LIST_MANAGEMENT,apply_id,user_id);
		jdbc.update(SQL_INSERT_SUMMARY_DOCUMENTS,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 履歴書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateResume(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_RESUME,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 大学成績証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateUniversityTranriptsscripts(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_TRANRIPTSSCRIPTS,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 大学（卒業・卒業見込み）証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateUniversityDiploma(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_DIPLOMA,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * HCS成績証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateHcsTranscript(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_TRANSCRIPT,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * HCS卒業見込み証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateHcsDiploma(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_DIPLOMA,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 健康診断証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateHealthCertificate(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HEALTH_CERTIFICATE,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 出身高校成績証明書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateHighSchoolTranscript(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HIGH_SCHOOL_TRANSCRIPT,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 推薦書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateRecommendation(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_RECOMMENDATION,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * 個人情報等同意書を受け取った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateInformationAgreement(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_PERSONAL_INFORMATION_AGREEMENT,apply_id);
		return rowNumber;
		
	}
	
	/**
	 * すべての書類が揃った日時を登録する
	 * @param apply_id 申請ID
	 * @return rowNumber 変更に成功した件数
	 * @throws DataAccessException
	 */
	public int updateRequired(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_ALL_REQUIRED,apply_id);
		return rowNumber;
		
	}
}
