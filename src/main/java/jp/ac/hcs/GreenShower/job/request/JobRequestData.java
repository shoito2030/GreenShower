package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import lombok.Data;

@Data
public class JobRequestData {
	/**
	 * 申請ID
	 */
	private String apply_id;
	/**
	 * 申請者ID
	 */
	private String applicant_id;
	
	/**
	 * 状態
	 */
	private String status;
	
	/**
	 * 申請種別
	 */
	private String apply_type;
	
	/**
	 * 指摘事項
	 */
	private String indicate;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 * 活動開始日時
	 */
	private Date date_activity_from;
	
	/**
	 * 活動終了日時
	 */
	private Date date_activity_to;
	
	
	/**
	 * 場所
	 */
	private String loc;
	
	/**
	 * 手段
	 */
	private String means;
	
	/**
	 * メモ
	 */
	private String remark;
	
	/**
	 * 欠席開始日時
	 */
	private Date date_absence_from;
	
	/**
	 * 欠席終了日時
	 */
	private Date date_absence_to;
	
	/**
	 * 早退日時
	 */
	private Date leave_early_date;
	
	/**
	 * 出席日時
	 */
	private Date attendance_date;
	
	/**
	 * 登録日時
	 */
	private Date register_date;
	
	/**
	 * 登録者のユーザID
	 */
	private String register_user_id;
	
	/**
	 * 更新日時
	 */
	private Date update_date;
	
	/**
	 * 更新者のユーザID
	 */
	private String update_user_id;
}
