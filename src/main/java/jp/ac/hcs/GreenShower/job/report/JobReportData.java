package jp.ac.hcs.GreenShower.job.report;

import java.util.Date;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class JobReportData extends JobHuntingData {
	
	/**
	 * 進退
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
	private Date register_user_id;
	
	/**
	 * 更新日時
	 */
	private Date update_date;
	
	/**
	 * 更新者のユーザID
	 */
	private Date update_user_id;

	@Override
	public String toString() {
		return "JobReportData [apply_id=" + apply_id + ", advance_or_retreat=" + advance_or_retreat + ", remark="
				+ remark + ", register_date=" + register_date + ", register_user_id=" + register_user_id
				+ ", update_date=" + update_date + ", update_user_id=" + update_user_id + ", classroom=" + classroom
				+ ", class_number=" + class_number + ", name=" + name + ", company_name=" + company_name + ", content="
				+ content + ", applicant_id=" + applicant_id + ", status=" + status + ", apply_type=" + apply_type
				+ "]";
	}

	
}
