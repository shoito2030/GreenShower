package jp.ac.hcs.GreenShower.job.report;

import lombok.Data;

/**
 * 就職活動申請（報告）処理において画面からの入力を受け取るFormクラス
 *
 */
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
