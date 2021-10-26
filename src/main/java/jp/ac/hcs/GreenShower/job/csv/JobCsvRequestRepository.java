package jp.ac.hcs.GreenShower.job.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobCsvRequestRepository {
	
	/** SQL 申請情報全件取得 */
	private static final String SQL_SELECT_ALL = "SELECT * FROM REQUESTS;";
	
	/** CSV出力用SQL */
	private static final String SQL_SELECT_CSV = "SELECT * FROM JOB_HUNTING JH,REQUESTS REQ, REPORTS REP, USERS U WHERE JH.APPLY_ID = REQ.APPLY_ID AND JH.APPLY_ID = REP.APPLY_ID AND JH.APPLICANT_ID = U.USER_ID AND JH.STATUS = '7';";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * requestsテーブル、job_huntingテーブルから報告完了した申請データを全件取得し、CSVファイルとしてサーバに保存する.
	 * @throws DataAccessException
	 */
	public void requestListCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		JobCsvRequestRowCallbackHandler handler = new JobCsvRequestRowCallbackHandler();

		jdbc.query(SQL_SELECT_CSV, handler);
	}
}
