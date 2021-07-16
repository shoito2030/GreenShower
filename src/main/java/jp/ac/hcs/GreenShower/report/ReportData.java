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
	private String entry_section;
	
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
	private String venue_section;
	
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
	 * 5：最終試験
	 * 99：その他（〇次試験）
	 */
	private String test_section;
	
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
	private String test_summary;
	
	/**
	 * 試験概要その他
	 */
	private String test_summary_other;
	
	/**
	 * 結果通知方法
	 * 1：〇日後
	 * 2：〇日後：合格のみ
	 * 3：本人
	 * 4：学校
	 * 5：電話
	 * 6：郵送
	 * 7：メール
	 * 8：Web
	 */
	private String result_notification;
	
	/**
	 * 1-2の数字
	 */
	private String result_notification_day;
	
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
	private String aptitude_test_detail;
	
	/**
	 * 適性試験詳細その他
	 */
	private String aptitude_test_detail_other;
	
	/**
	 * 面接詳細
	 * 1：形態：個
	 * 2：形態：集団
	 * 3：形態：その他
	 * 4：面接官：人数
	 * 5：面接官：役職
	 * 6：面接時間
	 * 7：GD／GW時のテーマ
	 */
	private String interview_detail;
	
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
	private String report_status;
	
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
}




















