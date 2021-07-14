package jp.ac.hcs.GreenShower.report;

import lombok.Data;

@Data
public class ReportData {
	/**
	 * 受験報告ID
	 */
	private String report_id;
	
	/**
	 * ユーザID(メールアドレス)
	 * メールアドレス形式
	 */
	private String user_id;
	
	/**
	 * 所属クラス
	 * 固定長文字列(4)
	 */
	private String classroom;
	
	/**
	 * 出席番号
	 * 固定長文字列(2)
	 */
	private String class_number;
	
	/**
	 * ユーザ名（氏名）
	 */
	private String name;
}
