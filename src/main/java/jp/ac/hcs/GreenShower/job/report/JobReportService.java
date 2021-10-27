package jp.ac.hcs.GreenShower.job.report;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 就職活動申請報告に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobReportService {

	@Autowired
	JobReportRepository jobReportRepository;

	/**
	 * 就職活動申請の報告情報を全件取得する
	 * 
	 * @param role アクターの権限
	 * @return Optional<jobReportEntity>
	 */
	public Optional<JobReportEntity> selectAllReports() {
		JobReportEntity jobReportEntity;

		try {
			jobReportEntity = jobReportRepository.selectAllReports();
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobReportEntity = null;
		}
		return Optional.ofNullable(jobReportEntity);
	}
	
	/**
	 * 就職活動申請の申請情報を自分の分取得する
	 * 
	 * @param role アクターの権限
	 * @return Optional<jobReportEntity>
	 */
	public Optional<JobReportEntity> selectStudentReports(String name) {
		JobReportEntity jobReportEntity;

		try {
			jobReportEntity = jobReportRepository.selectStudentReports(name);
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobReportEntity = null;
		}
		return Optional.ofNullable(jobReportEntity);
	}
	
	/**
	 * 報告マスタに新たな報告情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
//	public boolean insert(ReportForm form, String register_user_id) {
//		int rowNumber = 0;
//
//		try {
//			// 追加処理を行い、追加できた件数を取得
//			rowNumber = reportRepository.insertOne(refillToReportData(form, register_user_id));
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//		return rowNumber > 0;
//	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * 
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}



}
