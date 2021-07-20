package jp.ac.hcs.GreenShower.report;

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
public class TaskRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {

			File directory = new File(WebConfig.OUTPUT_PATH);
			if (!directory.exists()) {
				directory.mkdir();
			}

			File file = new File(WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			do {
				// Taskテーブルのデータ構造
				String str = rs.getInt("report_id") + "," + rs.getString("user_id") + "," + rs.getString("classroom")
						+ "," + rs.getString("class_number") + "," + rs.getString("name") + ","
						+ rs.getString("course_code") + "," + rs.getString("company_name") + ","
						+ rs.getString("company_name_kana") + "," + rs.getDate("datetime") + "," + rs.getString("place")
						+ "," + rs.getString("entry_section") + "," + rs.getString("entry_section_other") + ","
						+ rs.getString("venue_section") + "," + rs.getString("venue_section_other") + ","
						+ rs.getString("test_section") + "," + rs.getString("test_section_other") + ","
						+ rs.getString("test_summary") + "," + rs.getString("test_summary_other") + ","
						+ rs.getString("result_notification") + "," 
						//+ rs.getString("result_notification_day") + ","
						+ rs.getString("aptitude_test_detail") + "," + rs.getString("interview_detail") + ","
						+ rs.getString("interview_detail_other") + "," + rs.getInt("interview_number") + ","
						+ rs.getInt("interviewer_number") + "," + rs.getString("interviewer_position") + ","
						+ rs.getInt("interview_time") + "," + rs.getString("theme") + ","
						+ rs.getString("question_contents") + "," + rs.getString("report_status") + ","
						+ rs.getDate("registered_date") + "," + rs.getDate("request_date") + ","
						+ rs.getString("registered_user_id") + "," + rs.getString("remarks");

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