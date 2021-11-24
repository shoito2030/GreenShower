package jp.ac.hcs.GreenShower.job.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobExtraRepository {
	
	private static final String SQL_INSERT_SUMMARY_LIST_MANAGEMENT = "INSERT INTO SUMMARY_LIST_MANAGEMENT(APPLY_ID,REGISTER_DATE,REGISTER_USER_ID) VALUES(?,CURRENT_TIMESTAMP,?)";
	private static final String SQL_INSERT_SUMMARY_DOCUMENTS = "INSERT INTO SUMMARY_DOCUMENTS(APPLY_ID) VALUES(?)";
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
	
	public int listRegistion(String apply_id,String user_id) {
		int rowNumber = jdbc.update(SQL_INSERT_SUMMARY_LIST_MANAGEMENT,apply_id,user_id);
		jdbc.update(SQL_INSERT_SUMMARY_DOCUMENTS,apply_id);
		return rowNumber;
		
	}
	
	public int updateResume(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_RESUME,apply_id);
		return rowNumber;
		
	}
	
	public int updateUniversityTranriptsscripts(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_TRANRIPTSSCRIPTS,apply_id);
		return rowNumber;
		
	}
	
	public int updateUniversityDiploma(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_UNIVERSITY_DIPLOMA,apply_id);
		return rowNumber;
		
	}
	
	public int updateHcsTranscript(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_TRANSCRIPT,apply_id);
		return rowNumber;
		
	}
	
	public int updateHcsDiploma(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HCS_DIPLOMA,apply_id);
		return rowNumber;
		
	}
	
	public int updateHealthCertificate(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HEALTH_CERTIFICATE,apply_id);
		return rowNumber;
		
	}
	
	public int updateHighSchoolTranscript(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_HIGH_SCHOOL_TRANSCRIPT,apply_id);
		return rowNumber;
		
	}
	
	public int updateRecommendation(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_RECOMMENDATION,apply_id);
		return rowNumber;
		
	}
	
	public int updateInformationAgreement(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_PERSONAL_INFORMATION_AGREEMENT,apply_id);
		return rowNumber;
		
	}
	
	public int updateRequired(String apply_id) {
		int rowNumber = jdbc.update(SQL_UPDATE_SUMMARY_DOCUMENTS_ALL_REQUIRED,apply_id);
		return rowNumber;
		
	}
}
