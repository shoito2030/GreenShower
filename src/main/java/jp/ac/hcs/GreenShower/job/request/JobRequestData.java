package jp.ac.hcs.GreenShower.job.request;

import java.util.Date;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
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
	


	@Override
	public String toString() {
		return "JobRequestData [date_activity_from=" + date_activity_from + ", date_activity_to=" + date_activity_to
				+ ", loc=" + loc + ", date_absence_from=" + date_absence_from + ", date_absence_to=" + date_absence_to
				+ ", leave_early_date=" + leave_early_date + ", attendance_date=" + attendance_date + ", remark="
				+ remark + "]";
	}


}
