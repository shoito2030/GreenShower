package jp.ac.hcs.GreenShower.job.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class JobExtraService {

	@Autowired
	JobExtraRepository jobExtraRepository;
	
	
//	/**
//	 * 申請IDに一致したとりまとめ書類マスタを一件取得する
//	 * 
//	 * @param apply_id 申請ID
//	 * @return - 処理成功時：JobExtraDataを持つOptional - 処理失敗時、不正な操作時：空のOptional
//	 */
//	public Optional<JobExtraData> selectSummaryDocuments(String apply_id) {
//		JobExtraData jobExtraData;
//
//		try {
//			jobExtraData = jobExtraRepository.selectSummaryDocuments(apply_id);
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			return Optional.empty();
//		}
//		return Optional.ofNullable(jobExtraData);
//	}
	
	/**
	 * 新たに学生をとりまとめ名簿に登録した日時をデータベースに登録する
	 * @param apply_id 申請ID
	 * @param user_id  ユーザID
	 * @return - true：追加件数1件（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean listRegistion(String apply_id,String user_id) {
		int rowNumber = 0;
		try {
			rowNumber = jobExtraRepository.listRegistion(apply_id,user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		return rowNumber > 0;
	}
	
	/**
	 * 受け取った書類の日時をデータベースに登録する
	 * @param form     入力情報
	 * @param apply_id 申請ID
	 * @param user_id  ユーザID
	 * @return - true：追加件数1件（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean documentReceirt(JobExtraForm form,String apply_id,String user_id) {
		int rowNumber = 0;
		try {
			if(form.isReceipt_of_resume()) {
				rowNumber = jobExtraRepository.updateResume(apply_id);
			}
			if(form.isReceipt_of_university_tranriptsscripts()) {
				rowNumber = jobExtraRepository.updateUniversityTranriptsscripts(apply_id);
			}
			if(form.isReceipt_of_university_diploma()) {
				rowNumber = jobExtraRepository.updateUniversityDiploma(apply_id);
			}
			if(form.isReceipt_of_hcs_diploma()) {
				rowNumber = jobExtraRepository.updateHcsDiploma(apply_id);
			}
			if(form.isReceipt_of_hcs_transcript()) {
				rowNumber = jobExtraRepository.updateHcsTranscript(apply_id);
			}
			if(form.isReceipt_of_health_certificate()) {
				rowNumber = jobExtraRepository.updateHealthCertificate(apply_id);
			}
			if(form.isReceipt_of_high_school_transcript()) {
				rowNumber = jobExtraRepository.updateHighSchoolTranscript(apply_id);
			}
			if(form.isReceipt_of_recommendation()) {
				rowNumber = jobExtraRepository.updateRecommendation(apply_id);
			}
			if(form.isReceipt_of_personal_information_agreement()) {
				rowNumber = jobExtraRepository.updateInformationAgreement(apply_id);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		return rowNumber > 0;
	}
}
