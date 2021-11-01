package jp.ac.hcs.GreenShower.job.report;

import java.util.Date;

import lombok.Data;

@Data
public class JobReportForm {
	/**
	 * 申請ID
	 */
	private String apply_id;
	
	/**
	 * 進退
	 * TRUE:進める
	 * FALSE：進めない
	 */
	private boolean advance_or_retreat;
	
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
	private String register_user_id;
	
	/**
	 * 更新日時
	 */
	private Date update_date;
	
	/**
	 * 更新者のユーザID
	 */
	private String update_user_id;
	
	/**
	 * 状態
	 */
	protected String status;
	
	/**
	 * 指摘事項
	 */
	private String indicate;
}
