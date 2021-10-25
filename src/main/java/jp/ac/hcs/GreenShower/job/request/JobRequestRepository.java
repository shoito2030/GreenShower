package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


	public JobRequestEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		JobRequestEntity jobRequestEntity = mappingSelectResult(resultList);
		return jobRequestEntity;
	}


	private JobRequestEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		JobRequestEntity entity = new JobRequestEntity();
		
		for (Map<String, Object> map : resultList) {
			JobRequestData data = new JobRequestData();
			
			data.setRequest_type((String) map.get("request_type"));
			data.setCompany_name((String) map.get("company_name"));
			data.setStart((Date) map.get("start"));
			data.setEnd((Date) map.get("end"));
			data.setZipcode((String) map.get("zipcode"));
			data.setPlace((String) map.get("place"));
			data.setMeans((String) map.get("means"));
			data.setMemorandum((String) map.get("memorandum"));
			
		}
		return null;
	}
}
