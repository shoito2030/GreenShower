package jp.ac.hcs.GreenShower.job.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import jp.ac.hcs.GreenShower.job.validations.DateValueValid;
import lombok.Data;

/**
 * 就職活動申請（申請）処理において画面からの入力を受け取るFormクラス
 *
 */
@Data
public class JobRequestForm {
	
	/**
	 * 申請ID
	 */
	protected String apply_id;
	
	/**
	 * クラス
	 */
	protected String classroom;
	
	/**
	 * 出席番号
	 */
	private String class_number;
	
	/**
	 * 氏名
	 */
	protected String name;
	
	/**
	 * 申請種別
	 */
	@NotBlank(message = "選択してください")
	private String apply_type;
	
	/**
	 * 企業名
	 */
	@NotBlank(message = "企業名を入力してください")
	private String company_name;
	
	/**
	 * 開始時刻
	 */
	@NotBlank(message = "選択してください")
	@DateValueValid
	private String date_activity_from;
	
	/**
	 * 終了日時
	 */
	@NotBlank(message = "選択してください")
	@DateValueValid
	private String date_activity_to;
	
	/**
	 * 場所
	 */
	@NotBlank(message = "場所を入力してください")
	private String loc;
	
	/**
	 * 手段
	 */
	@NotEmpty(message = "手段を選択してください")
	private List<String> way = new ArrayList<>();
	
	/**
	 * 内容
	 */
	@NotBlank(message = "内容を選択してください")
	private String content;
	
	/**
	 * 欠席開始日時
	 */
	@DateValueValid
	private String date_absence_from;
	
	/**
	 * 欠席終了日時
	 */
	@DateValueValid
	private String date_absence_to;
	
	/**
	 * 早退日時
	 */
	@DateValueValid
	private String leave_early_date;
	
	/**
	 * 遅刻日時
	 */
	@DateValueValid
	private String attendance_date;
	
	/**
	 * メモ
	 */
	@Length(max=254, message="メモが長すぎます。")
	private String remark;
	
	/**
	 * 変更
	 */
	private String status;
	
	/**
	 * 指摘欄
	 */
	@Length(max=254, message="指摘が長すぎます。")
	private String indicate;
}
