package jp.ac.hcs.GreenShower.job.report;

import org.hibernate.validator.constraints.Length;

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
	@Length(max=254, message="備考が長すぎます。")
	private String remark;
	
	/**
	 * 状態
	 */
	private String status;
	
	/**
	 * 指摘事項
	 */
	@Length(max=254, message="指摘が長すぎます。")
	private String indicate;
	
}
