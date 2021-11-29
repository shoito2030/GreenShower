package jp.ac.hcs.GreenShower.job.common;

import lombok.Data;

/**
 * 就職活動申請マスタの情報を取り扱うDAO
 */
@Data
public class JobHuntingData {
	// 下記はアクターが担任の場合のみ必要になることがある
	
	/**
	 * クラス
	 */
	protected String classroom;
	
	/**
	 * 出席番号
	 */
	protected String class_number;
	
	/**
	 * 氏名
	 */
	protected String name;
	
	// 下記は共通
	/**
	 * 企業名
	 */
	protected String company_name;
	
	/**
	 * 内容
	 */
	protected Content content;
	
	/**
	 * 申請ID
	 */
	protected String apply_id;
	
	/**
	 * 申請者ID
	 */
	protected String applicant_id;
	
	/**
	 * 状態
	 */
	protected Status status;
	
	/**
	 * 申請種別
	 */
	protected Apply_type apply_type;
	
	/**
	 * 指摘事項
	 */
	protected String indicate;
	
	/**
	 *状態
	 *
	 */
	public enum Status implements CommonEnum<Status> {
		
		// 名前募集
		/** 状態 1 申請作成中 */
		ONE("1","申請作成中"),
		/** 状態 2 申請承認待 */
		TWO("2","申請承認待"),
		/** 状態 3 申請承認済 */
		THREE("3","申請承認済"),
		/** 状態 4 申請完了 */
		FOUR("4","申請完了"),
		/** 状態 5 報告作成中 */
		FIVE("5","報告作成中"),
		/** 状態 6 報告承認待 */
		SIX("6","報告承認待"),
		/** 状態 7 報告完了 */
		SEVEN("7","報告完了"),
		/** 状態 99 取消済 */
		OTHER("99","取消済");
		
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
	
	/**
	 * 申請種別
	 */
	public enum Apply_type implements CommonEnum<Apply_type>{
		/** 申請種別 0 個人 */
		PERSONAL("0","個人"),
		/** 申請種別 1 とりまとめ */
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
	/**
	 * 内容
	 */
	public enum Content implements CommonEnum<Content>{
		/** 内容 1 合企 */
		MULTIPLE_COMPANY_SESSION("1","合企"),
		/** 内容 2 単独 */
		SINGLE_COMPANY_SESSION("2","単独"),
		/** 内容 3 試験 */
		TEST("3","試験"),
		/** 内容 99 その他 */
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
