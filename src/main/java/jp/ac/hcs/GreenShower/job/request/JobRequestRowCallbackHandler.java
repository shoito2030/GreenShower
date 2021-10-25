package jp.ac.hcs.GreenShower.job.request;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import jp.ac.hcs.GreenShower.WebConfig;
/**
 * SQLで取得した結果をCSVファイルとしてサーバに保存する.
*/
public class JobRequestRowCallbackHandler implements RowCallbackHandler {


		@Override
		public void processRow(ResultSet rs) throws SQLException {

			try {

				File directory = new File(WebConfig.OUTPUT_PATH);
				if (!directory.exists()) {
					directory.mkdir();
				}

				File file = new File(WebConfig.OUTPUT_PATH + WebConfig.FILENAME_JOBREQUEST_CSV);
				FileWriter fw = new FileWriter(file.getAbsoluteFile());

				BufferedWriter bw = new BufferedWriter(fw);
				do {
					// 申請一覧テーブルのデータ構造
					String str =rs.getString("request_type") + ","
					+ rs.getString("company_name") + ","
					+ rs.getDate("start") + ","
					+ rs.getDate("end") + "," 
					+ rs.getString("zipcode")
					+ rs.getString("place") + ","
					+ rs.getString("means") + ","
					+ rs.getString("memorandum");

					bw.write(str);
					bw.newLine();
				} while (rs.next());
				bw.flush();
				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}
		}
}

