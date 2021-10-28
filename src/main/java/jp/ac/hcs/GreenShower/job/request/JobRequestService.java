package jp.ac.hcs.GreenShower.job.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 就職活動申請に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobRequestService {

	@Autowired
	JobRequestRepository jobRequestRepository;

	/**
	 * 就職活動申請の申請情報を全件取得する
	 * 
	 * @return Optional<jobRequestEntity>
	 */
	public Optional<JobRequestEntity> selectAllRequests() {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectAllRequests();
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}
	
	/**
	 * 就職活動申請の申請情報を自分の分取得する
	 * 
	 * @param user_id ユーザID
	 * @return Optional<jobRequestEntity>
	 */
	public Optional<JobRequestEntity> selectStudentRequests(String user_id) {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectStudentRequests(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}

	/**
	 * 申請マスタに新たな報告情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(JobRequestForm form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = jobRequestRepository.insertOne(refillToJobReportData(form, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
		
	}

	/**
	 * 入力情報をJobReportData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return JobReportData
	 */
	private JobRequestData refillToJobReportData(JobRequestForm form, String register_user_id) {
		JobRequestData data = new JobRequestData();
		return data;
	}


//	
//	/**
//	 * 就職活動申請の状態変更処理を行う
//	 * 
//	 * @param user_id ユーザID
//	 * @return Optional<jobRequestEntity>
//	 */
//	public boolean updateJobStatus(String apply_id,JobRequestForm form){
//		boolean success=false;
//		try {
//			success = jobRequestRepository.updateJobStatus(report_id,form);
//		}catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//		return success;
//		
//	}

}
