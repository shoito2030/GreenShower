package jp.ac.hcs.GreenShower.job.report;

import java.util.Date;

import lombok.Data;

@Data
public class JobReportData {
	
	/**
	 * 申請ID
	 */
	private String apply_id;
	
	/**
	 * 進退
	 */
	private String advance_or_retreat;
	
	/**
	 * 備考
	 */
	private String remark;
	
	/**
	 * 登録日時
	 */
	private Date register_date;
	
	/**
	 * 登録者のユーザID
	 */
	private Date register_user_id;
	
	/**
	 * 更新日時
	 */
	private Date update_date;
	
	/**
	 * 更新者のユーザID
	 */
	private Date update_user_id;
}
