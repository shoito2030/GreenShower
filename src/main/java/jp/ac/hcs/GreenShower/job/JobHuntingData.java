package jp.ac.hcs.GreenShower.job;

import lombok.Data;

/**
 * 就職活動申請マスタの情報を取り扱うクラス
 */
@Data
public class JobHuntingData {
	// 下記はアクターが担任の場合のみ必要になることがある
	
	/**
	 * クラス
	 */
	private String classroom;
	
	/**
	 * 出席番号
	 */
	private String class_number;
	
	/**
	 * 氏名
	 */
	private String name;
	
	// 下記は共通
	
	/**
	 * 申請ID
	 */
	private String apply_id;
	
	/**
	 * 申請者ID
	 */
	private String applicant_id;
	
	/**
	 * 状態
	 */
	private Status status;
	
	/**
	 * 申請種別
	 */
	private Apply_type apply_type;
	
	/**
	 * 指摘事項
	 */
	private String indicate;
	
	public enum Status implements CommonEnum<Status> {
		
		// 名前募集
		ONE("1","申請作成中"),
		TWO("2","申請承認待"),
		THREE("3","申請承認済"),
		FOUR("4","申請完了"),
		FIVE("5","報告作成中"),
		SIX("6","報告承認待"),
		SEVEN("7","報告完了"),
		OTHER("99","その他");
		
		/** ID */
		private String id;
		
		/** 値 */
		private String value;

		/** コンストラクタ */
		Status(String id, String value) {
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
	
	public enum Apply_type implements CommonEnum<Apply_type>{
		PERSONAL("0","個人"),
		SUMMARY("1","とりまとめ");
		
		/** ID */
		private String id;
		
		/** 値 */
		private String value;

		/** コンストラクタ */
		Apply_type(String id, String value) {
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
