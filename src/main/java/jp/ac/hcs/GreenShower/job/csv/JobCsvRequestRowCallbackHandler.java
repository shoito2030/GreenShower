package jp.ac.hcs.GreenShower.job.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import jp.ac.hcs.GreenShower.WebConfig;

public class JobCsvRequestRowCallbackHandler implements RowCallbackHandler{
	
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
				String str =rs.getString("apply_id") + ","
				+ rs.getDate("date_activity_from") + ","
				+ rs.getDate("date_activity_to") + ","
				+ rs.getString("loc") + ","
				+ rs.getString("content") + ","
				+ rs.getString("company_name")
				+ rs.getDate("date_absence_from") + ","
				+ rs.getDate("date_absence_to") + ","
				+ rs.getDate("leave_early_date") + ","
				+ rs.getDate("attendance_date") + ","
				+ rs.getString("remark");

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