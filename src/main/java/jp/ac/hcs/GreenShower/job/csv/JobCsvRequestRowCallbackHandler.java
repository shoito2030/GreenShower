package jp.ac.hcs.GreenShower.job.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import jp.ac.hcs.GreenShower.WebConfig;
import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData;

/**
 * データベースの内容からCSVファイルを作成する
 *
 */
public class JobCsvRequestRowCallbackHandler implements RowCallbackHandler{
	
	/**
	 * データベースの内容からCSVファイルを作成する
	 * @param rs 申請一覧テーブルのデータ
	 * @throws SQLException データベース・アクセス・エラーまたはその他のエラーに関する情報を提供する例外
	 */
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
			
			String name = "申請ID,申請種別,名前,クラス,番号,活動開始日時,活動終了日時,場所,内容,企業名,欠席開始日時,欠席終了日時,早退日時,出席日時,メモ";
			bw.write(name);
			bw.newLine();
			
			do {
				// 申請一覧テーブルのデータ構造
				/** 申請ID、申請種別、名前、クラス、番号、活動開始日時、活動終了日時、場所、内容、
				 * 企業名、欠席開始日時、欠席終了日時、早退日時、出席日時、
				 * メモ
				 * */
				String str = rs.getString("apply_id") + ","
				+ CommonEnum.getEnum(JobHuntingData.Apply_type.class, (String) rs.getString("apply_type")).getValue() + ","
				+ rs.getString("name") + ","
				+ rs.getString("classroom") + ","
				+ rs.getString("class_number") + ","
				+ rs.getDate("date_activity_from") + ","
				+ rs.getDate("date_activity_to") + ","
				+ rs.getString("loc") + ","
				+ CommonEnum.getEnum(JobHuntingData.Content.class, (String) rs.getString("content")).getValue() + ","
				+ rs.getString("company_name") + ","
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
