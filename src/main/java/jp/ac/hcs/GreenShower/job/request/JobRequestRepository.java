package jp.ac.hcs.GreenShower.job.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.report.ReportRowCallbackHandler;

@Repository
public class JobRequestRepository {
	
	/** SQL 全件取得（クラスと出席番号の昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM jobrequest";
	
	@Autowired
	private JdbcTemplate jdbc;
	
	
	/**
	 * TaskテーブルからユーザIDをキーにデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void reportlistCsvOut() throws DataAccessException {

		// CSVファイル出力用設定
		ReportRowCallbackHandler handler = new ReportRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL, handler);
	}
}
