package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import jp.ac.hcs.GreenShower.job.CommonEnum;
import jp.ac.hcs.GreenShower.job.JobHuntingData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class JobRequestData extends JobHuntingData{
	
	/**
	 * 活動開始日時
	 */
	private Date date_activity_from;
	
	/**
	 * 活動終了日時
	 */
	private Date date_activity_to;
	
	/**
	 * 場所
	 */
	private String loc;
	
//	/**
//	 * 手段
//	 */
//	private String means;
	
	/**
	 * 内容
	 */
	private Content content;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
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
	private Date leave_early_date;
	
	/**
	 * 出席日時
	 */
	private Date attendance_date;
	
	/**
	 * メモ
	 */
	private String remark;
	
//	/**
//	 * 登録日時
//	 */
//	private Date register_date;
//	
//	/**
//	 * 登録者のユーザID
//	 */
//	private String register_user_id;
//	
//	/**
//	 * 更新日時
//	 */
//	private Date update_date;
//	
//	/**
//	 * 更新者のユーザID
//	 */
//	private String update_user_id;
	
	public enum Content implements CommonEnum<Content>{
		MULTIPLE_COMPANY_SESSION("1","合企"),
		SINGLE_COMPANY_SESSION("2","単独"),
		TEST("3","試験"),
		OTHER("99","その他");
		
		/** ID */
		private String id;
		
		/** 値 */
		private String value;

		/** コンストラクタ */
		Content(String id, String value) {
			this.id = id;
			this.value = value;
		}
		
		@Override
		public String getId() {
			return this.id;
		}

		@Override
		public String getValue() {
			return this.value;
		}
	}
}
