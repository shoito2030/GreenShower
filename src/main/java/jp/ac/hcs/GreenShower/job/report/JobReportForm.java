package jp.ac.hcs.GreenShower.job.report;

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
	 * 状態
	 */
	private String status;
	
	/**
	 * 指摘事項
	 */
	private String indicate;
	
}
