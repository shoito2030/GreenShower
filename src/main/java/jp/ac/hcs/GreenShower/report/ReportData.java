package jp.ac.hcs.GreenShower.report;

import java.util.Date;

import lombok.Data;

@Data
public class ReportData {
	/**
	 * 受験報告ID
	 */
	private int report_id;
	
	/**
	 * 求人番号
	 */
	private String job_number;
	
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
	
	/**
	 * 学科コード
	 */
	private String course_code;
	
	/**
	 * 企業名
	 */
	private String company_name;
	
	/**
	 * 企業名カナ
	 */
	private String company_name_kana;
	
	/**
	 * 日時
	 */
	private Date datetime;
	
	/**
	 * 受験場所
	 */
	private String place;
	
	/**
	 * 申し込み区分
	 * 1：学校斡旋
	 * 2：ネット：斡旋サイト
	 * 3：ネット：企業HP
	 * 4：新聞・雑誌
	 * 5：ジョブカフェ等
	 * 99：その他
	 */
	private Entry_section entry_section;
	
	/**
	 * 申し込み区分その他
	 */
	private String entry_section_other;
	
	/**
	 * 会場区分
	 * 1：札幌：会社
	 * 2：札幌：外会場
	 * 3：札幌：HCS
	 * 4：東京地区：会社
	 * 5：東京地区：外会場
	 * 99：その他
	 */
	private Venue_section venue_section;
	
	/**
	 * 会場区分その他
	 */
	private String venue_section_other;
	
	/**
	 * 試験区分
	 * 1：1次試験
	 * 2：2次試験
	 * 3：3次試験
	 * 4：4次試験
	 * 99：その他（〇次試験）
	 */
	private Test_section test_section;
	
	/**
	 * 最終試験
	 * -true 最終試験である
	 * -false 最終試験ではない
	 */
	private boolean test_final;
	
	/**
	 * 試験区分その他
	 */
	private String test_section_other;
	
	/**
	 * 試験概要
	 * 1：適性
	 * 2：筆記・作文
	 * 3：ディスカッション
	 * 4：グループワーク
	 * 5：集団面接
	 * 6：個人面接
	 * 99：その他
	 */
	private Test_summary test_summary;
	
	/**
	 * 試験概要その他
	 */
	private String test_summary_other;
	
	/**
	 * 結果通知方法
	 * 1：本人
	 * 2：学校
	 * 3：電話
	 * 4：郵送
	 * 5：メール
	 * 6：Web
	 */
	private Result_notification result_notification;
	
	/**
	 * -true 合格のみ
	 * -false 不合格も
	 */
	private boolean success_only;
	
	/**
	 * 1-2の数字
	 */
	private int result_notification_day;
	
	/**
	 * 適性試験詳細
	 * 1：SPI
	 * 2：CAB／GAB
	 * 3：職業適性
	 * 4：クレペリン
	 * 5：一般常識
	 * 6：専門知識
	 * 7：作文テーマ
	 * 99：その他適性
	 */
	private Aptitude_test_detail aptitude_test_detail;
	
	/**
	 * 適性試験詳細その他
	 */
	private String aptitude_test_detail_other;
	
	/**
	 * 面接詳細
	 * 1：形態：個
	 * 2：形態：集団
	 * 3：形態：その他
	 */
	private Interview_detail interview_detail;
	
	/**
	 * 面接詳細その他
	 */
	private String interview_detail_other;
	
	/**
	 * 面接に参加した学生の数
	 */
	private int interview_number;
	
	/**
	 * 面接官の数
	 */
	private int interviewer_number;
	
	/**
	 * 面接官の役職
	 */
	private String interviewer_position;
	
	/**
	 * 面接時間
	 */
	private int interview_time;
	
	/**
	 * GD／GW時のテーマ
	 */
	private String theme;
	
	/**
	 * 出題内容
	 */
	private String question_contents;
	
	/**
	 * 受験報告状態
	 * 1：新規作成
	 * 2：差し戻し
	 * 3：承認待ち
	 * 4：承認済み
	 */
	private Report_status report_status;
	
	/**
	 * 登録日時
	 */
	private Date registered_date;
	
	/**
	 * 申請日
	 */
	private Date request_date;
	
	/**
	 * 登録者のユーザID
	 */
	private String registered_user_id;
	
	/**
	 * 備考
	 */
	private String remarks;
	
	enum Entry_section {
		ONE(1,"学校斡旋"),
		TWO(2,"ネット：斡旋サイト"),
		THREE(3,"ネット：企業HP"),
		FOUR(4,"新聞・雑誌"),
		FIVE(5,"ジョブカフェ等"),
		OTHER(99,"その他");

		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Entry_section (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したEntry_section型を返却する
		 * @param id
		 * @return Entry_section
		 */
		public static Entry_section idOf(int id) {
			for (Entry_section entry_section : values()) {
				if(entry_section.getId() == id) {
					return entry_section;
				}
			}
			throw new IllegalArgumentException("指定されたIDのEntry_sectionが存在しません");
		}
	}
	
	enum Venue_section {
		ONE(1,"札幌：会社"),
		TWO(2,"札幌：外会場"),
		THREE(3,"札幌：HCS"),
		FOUR(4,"東京地区：会社"),
		FIVE(5,"東京地区：外会場"),
		OTHER(99,"その他");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Venue_section (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したVenue_section型を返却する
		 * @param id
		 * @return Venue_section
		 */
		public static Venue_section idOf(int id) {
			for (Venue_section venue_section : values()) {
				if(venue_section.getId() == id) {
					return venue_section;
				}
			}
			throw new IllegalArgumentException("指定されたIDのVenue_sectionが存在しません");
		}
	}
	
	enum Test_section {
		ONE(1,"1次試験"),
		TWO(2,"2次試験"),
		THREE(3,"3次試験"),
		FOUR(4,"4次試験"),
		OTHER(99,"その他");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Test_section (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したTest_section型を返却する
		 * @param id
		 * @return Test_section
		 */
		public static Test_section idOf(int id) {
			for (Test_section test_section : values()) {
				if(test_section.getId() == id) {
					return test_section;
				}
			}
			throw new IllegalArgumentException("指定されたIDのTest_sectionが存在しません");
		}
	}
	
	enum Result_notification {
		ONE(1,"本人"),
		TWO(2,"学校"),
		THREE(3,"電話"),
		FOUR(4,"郵送"),
		FIVE(5,"メール"),
		SIX(6,"Web");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Result_notification (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したResult_notification型を返却する
		 * @param id
		 * @return Result_notification
		 */
		public static Result_notification idOf(int id) {
			for (Result_notification result_notification : values()) {
				if(result_notification.getId() == id) {
					return result_notification;
				}
			}
			throw new IllegalArgumentException("指定されたIDのResult_notificationが存在しません");
		}
	}
	
	enum Test_summary {
		ONE(1,"適性"),
		TWO(2,"筆記・作文"),
		THREE(3,"ディスカッション"),
		FOUR(4,"グループワーク"),
		FIVE(5,"集団面接"),
		SIX(6,"個人面接"),
		OTHER(99,"その他");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Test_summary (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したTest_summary型を返却する
		 * @param id
		 * @return Test_summary
		 */
		public static Test_summary idOf(int id) {
			for (Test_summary test_summary : values()) {
				if(test_summary.getId() == id) {
					return test_summary;
				}
			}
			throw new IllegalArgumentException("指定されたIDのTest_summaryが存在しません");
		}
	}
	
	enum Aptitude_test_detail {
		ONE(1,"SPI"),
		TWO(2,"CAB／GAB"),
		THREE(3,"職業適性"),
		FOUR(4,"クレペリン"),
		FIVE(5,"一般常識"),
		SIX(6,"専門知識"),
		SEVEN(7,"作文テーマ"),
		OTHER(99,"その他");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Aptitude_test_detail (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したAptitude_test_detail型を返却する
		 * @param id
		 * @return Aptitude_test_detail
		 */
		public static Aptitude_test_detail idOf(int id) {
			for (Aptitude_test_detail aptitude_test_detail : values()) {
				if(aptitude_test_detail.getId() == id) {
					return aptitude_test_detail;
				}
			}
			throw new IllegalArgumentException("指定されたIDのAptitude_test_detailが存在しません");
		}
	}
	
	enum Interview_detail {
		ONE(1,"形態：個人"),
		TWO(2,"形態：集団"),
		THREE(3,"形態：その他");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Interview_detail (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したInterview_detail型を返却する
		 * @param id
		 * @return Interview_detail
		 */
		public static Interview_detail idOf(int id) {
			for (Interview_detail interview_detail : values()) {
				if(interview_detail.getId() == id) {
					return interview_detail;
				}
			}
			throw new IllegalArgumentException("指定されたIDのInterview_detailが存在しません");
		}
	}
	
	enum Report_status {
		ONE(1,"承認待ち"),
		TWO(2,"承認済み"),
		THREE(3,"差し戻し"),
		FOUR(4,"破棄");
		
		/** ID */
		private int id;
		
		/** 値 */
		private String value;
		
		/** コンストラクタ */
		Report_status (int id, String value) {
			this.id = id;
			this.value = value;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.value;
		}
		
		/**
		 * IDから合致したReport_status型を返却する
		 * @param id
		 * @return Report_status
		 */
		public static Report_status idOf(int id) {
			for (Report_status report_status : values()) {
				if(report_status.getId() == id) {
					return report_status;
				}
			}
			throw new IllegalArgumentException("指定されたIDのReport_statusが存在しません");
		}
	}
	
}
