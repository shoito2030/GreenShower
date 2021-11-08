package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import lombok.Data;

@Data
public class EventData {
	/**
	 * イベントID
	 */
	private String event_id;
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 *日時
	 */
	private Date datetime;
	
	/**
	 * 場所
	 */
	private String loc;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 内容
	 */
	private String bring;
}
