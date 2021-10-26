package jp.ac.hcs.GreenShower.job.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobCsvRequestRepository {
	
	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM REQUESTS;";
	
	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL_REQUESTS = "SELECT * FROM JOB_HUNTING JH, REQUESTS REQ, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLICANT_ID  = U.USER_ID;";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * requestsテーブルからデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void requestListCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		JobCsvRequestRowCallbackHandler handler = new JobCsvRequestRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL_REQUESTS, handler);
	}
}
