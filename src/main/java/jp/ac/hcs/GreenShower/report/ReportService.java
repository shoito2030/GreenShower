package jp.ac.hcs.GreenShower.report;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.user.UserFormForInsert;


@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<ReportEntity>
	 */
	public Optional<ReportEntity> selectAll() {
		ReportEntity reportEntity;

		try {
			reportEntity = reportRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}
	
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<ReportData>
	 */
	public Optional<ReportData> selectOne(String report_id) {
		ReportData reportEntity;

		try {
			reportEntity = reportRepository.selectOne(report_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}
	
	/**
	 * ユーザマスタに新たなユーザ情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(UserFormForInsert form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = reportRepository.insertOne(refillToReportData(form, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
	}
	
	
	/**
	 * 入力情報をUserData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return UserData
	 */
	private ReportData refillToReportData(UserFormForInsert form, String register_user_id) {
		ReportData data = new ReportData();

		
		data.setRegistered_user_id(register_user_id);

		return data;
	}

}
