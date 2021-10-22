package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import lombok.Data;

@Data
public class JobRequestData {
	/**
	 * 申請種別
	 */
	private String request_type;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 * 開始日時
	 */
	private Date start;
	
	/**
	 * 終了日時
	 */
	private Date end;
	
	/**
	 * 郵便番号
	 */
	private String zipcode;
	
	/**
	 * 場所
	 */
	private String place;
	
	/**
	 * 手段
	 */
	private String means;
	
	/**
	 * メモ
	 */
	private String memorandum;
}
