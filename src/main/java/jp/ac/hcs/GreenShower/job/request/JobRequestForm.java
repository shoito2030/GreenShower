package jp.ac.hcs.GreenShower.job.request;

import lombok.Data;

@Data
public class JobRequestForm {
	/**
	 * 申請種別
	 */
	private String apply_type;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 * 開始時刻・終了日時
	 */
	private String date_activity_from;
	
	private String date_activity_to;
	
	/**
	 * 場所
	 */
	private String loc;
	
	/**
	 * 手段
	 */
	private String way;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 欠席開始日時
	 */
	private String date_absence_from;
	
	/**
	 * 欠席終了日時
	 */
	private String date_absence_to;
	
	/**
	 * 早退日時
	 */
	private String leave_eary_date;
	
	/**
	 * 遅刻日時
	 */
	private String attendance_date;
	
	/**
	 * メモ
	 */
	private String remark;
	
	/**
	 * 変更
	 */
	private String status;
	
	/**
	 * 指摘欄
	 */
	private String indicate;
}
