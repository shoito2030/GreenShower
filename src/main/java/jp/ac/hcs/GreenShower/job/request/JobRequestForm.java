package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import lombok.Data;

@Data
public class JobRequestForm {
	/**
	 * 申請種別
	 */
	private int apply_type;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 * 開始時刻・終了日時
	 */
	private Date date_activity;
	
	/**
	 * 場所
	 */
	private String loc;
	
	/**
	 * 手段
	 */
	private int way;
	
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
	private Date leave_eary_date;
	
	/**
	 * 遅刻日時
	 */
	private Date attendance_date;
	
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
