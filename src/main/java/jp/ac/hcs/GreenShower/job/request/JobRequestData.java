package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * １件の申請情報（申請マスタの１カラム）を管理するDAO
 *
 */
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
	
	/**
	 * 手段
	 */
	private Way way;
	
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
	
	/**　
	 *登録ユーザID
	 */
	private String register_user_id;
	
	public enum Way implements CommonEnum<Way>{
		ABSENCE("1","欠席"),
		LEAVE("2","早退"),
		LATE("3","遅刻"),
		LEAVE_AND_LATE("4","遅刻及び早退");
		
		/** ID */
		private String id;
		
		/** 値 */
		private String value;

		/** コンストラクタ */
		Way(String id, String value) {
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

	@Override
	public String toString() {
		return "JobRequestData [date_activity_from=" + date_activity_from + ", date_activity_to=" + date_activity_to
				+ ", loc=" + loc + ", way=" + way + ", date_absence_from=" + date_absence_from + ", date_absence_to="
				+ date_absence_to + ", leave_early_date=" + leave_early_date + ", attendance_date=" + attendance_date
				+ ", remark=" + remark + ", classroom=" + classroom + ", class_number=" + class_number + ", name="
				+ name + ", company_name=" + company_name + ", content=" + content + ", apply_id=" + apply_id
				+ ", applicant_id=" + applicant_id + ", status=" + status + ", apply_type=" + apply_type + "]";
	} 

}
