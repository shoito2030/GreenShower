package jp.ac.hcs.GreenShower.job.request;

import javax.validation.constraints.NotBlank;

import jp.ac.hcs.GreenShower.job.validations.DateValueValid;
import lombok.Data;

@Data
public class EventForm {
	/**
	 * 企業名
	 */
	@NotBlank
	private String company_name;
	
	/**
	 * 開始時刻
	 */
	@NotBlank
	@DateValueValid
	private String datetime;
	
	/**
	 * 場所
	 */
	@NotBlank
	private String loc;
	
	/**
	 * 内容
	 */
	@NotBlank
	private String content;
	
	/**
	 * 内容
	 */
	private String bring;
}
