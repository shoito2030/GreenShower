package jp.ac.hcs.GreenShower.job.extra;

import lombok.Data;

/**
 * 1件のとりまとめ書類を管理するDAO
 * 
 */
@Data
public class JobExtraData{

	/**
	 * 履歴書受領日時
	 * 
	 */
	private boolean receipt_of_resume;
	
	/**
	 * 大学成績証明書受領日時
	 * 
	 */
	private boolean receipt_of_university_tranriptsscripts;
	
	/**
	 * 大学（卒業・卒業見込）証明書受領日時
	 * 
	 */
	private boolean receipt_of_university_diploma;
	
	/**
	 * HCS成績証明書受領日時
	 * 
	 */
	private boolean receipt_of_hcs_transcript;
	
	/**
	 * HCS卒業見込証明書受領日時
	 * 
	 */
	private boolean receipt_of_hcs_diploma;
	
	/**
	 * 健康診断証明書受領日時
	 * 
	 */
	private boolean receipt_of_health_certificate;
	
	/**
	 * 出身高校成績証明書受領日時
	 * 
	 */
	private boolean receipt_of_high_school_transcript;
	
	/**
	 * 推薦書受領日時
	 * 
	 */
	private boolean receipt_of_recommendation;
	
	/**
	 * 個人情報等同意書受領日時
	 * 
	 */
	private boolean receipt_of_personal_information_agreement;
}
