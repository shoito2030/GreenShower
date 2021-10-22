package jp.ac.hcs.GreenShower.job.request;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class JobRequestRowCallbackHandler implements RowCallbackHandler {
	/**
	 * SQLで取得した結果をCSVファイルとしてサーバに保存する.
	 */

		@Override
		public void processRow(ResultSet rs) throws SQLException {

//			try {
//
//				File directory = new File(WebConfig.OUTPUT_PATH);
//				if (!directory.exists()) {
//					directory.mkdir();
//				}
//
//				File file = new File(WebConfig.OUTPUT_PATH + WebConfig.FILENAME_JOBREPORT_CSV);
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
//
//				BufferedWriter bw = new BufferedWriter(fw);
//				do {
//					// Taskテーブルのデータ構造
//					String str = rs.getInt("report_id") + "," + rs.getString("user_id") + "," + rs.getString("classroom")
//							+ "," + rs.getString("class_number") + "," + rs.getString("name") + ","
//							+ rs.getString("course_code") + "," + rs.getString("company_name") + ","
//							+ rs.getString("company_name_kana") + "," + rs.getDate("datetime") + "," + rs.getString("place")
//							+ "," + Entry_section.idOf(rs.getInt("entry_section")).getValue() + "," + rs.getString("entry_section_other") + ","
//							+ Venue_section.idOf(rs.getInt("venue_section")).getValue() +  "," + rs.getString("venue_section_other") + ","
//							+ Test_section.idOf(rs.getInt("test_section")).getValue() +  "," + rs.getString("test_section_other") + ","
//							+ Test_summary.idOf(rs.getInt("test_summary")).getValue() + "," + rs.getString("test_summary_other") + ","
//							+ Result_notification.idOf(rs.getInt("result_notification")).getValue() + "," 
//							//+ rs.getString("result_notification_day") + ","
//							+ Aptitude_test_detail.idOf(rs.getInt("aptitude_test_detail")).getValue() + "," + Interview_detail.idOf(rs.getInt("interview_detail")).getValue() + ","
//							+ rs.getString("interview_detail_other") + "," + rs.getInt("interview_number") + ","
//							+ rs.getInt("interviewer_number") + "," + rs.getString("interviewer_position") + ","
//							+ rs.getInt("interview_time") + "," + rs.getString("theme") + ","
//							+ rs.getString("question_contents") + "," + Report_status.idOf(rs.getInt("report_status")).getValue() + ","
//							+ rs.getDate("registered_date") + "," + rs.getDate("request_date") + ","
//							+ rs.getString("registered_user_id") + "," + rs.getString("remarks");
//
//					bw.write(str);
//					bw.newLine();
//				} while (rs.next());
//				bw.flush();
//				bw.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//				throw new SQLException(e);
//			}
		}
}

