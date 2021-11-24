package jp.ac.hcs.GreenShower.job.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobExtraRepository {
	
	private static final String SQL_INSERT_SUMMARY_LIST = "INSERT INTO SUMMARY_LIST_MANAGEMENT(APPLY_ID,REGISTER_DATE,REGISTER_USER_ID) VALUES(?,CURRENT_TIMESTAMP,?)";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public int listRegistion(String apply_id,String user_id) {
		int rowNumber = jdbc.update(SQL_INSERT_SUMMARY_LIST,apply_id,user_id);
		
		return rowNumber;
		
	}
}
