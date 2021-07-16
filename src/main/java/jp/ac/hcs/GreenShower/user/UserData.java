package jp.ac.hcs.GreenShower.user;

import java.util.Date;

import lombok.Data;

/**
 * 1件分のユーザ情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class UserData {
	/**
	 * ユーザID(メールアドレス) 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;

	/**
	 * パスワード 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String encrypted_password;

	/**
	 * ユーザ名 必須入力
	 */
	private String name;

	/**
	 * ダークモードフラグ - ON :true - OFF:false ユーザ作成時はfalse固定
	 */
	private boolean dark_mode;

	/**
	 * 権限 - 学生 : "ROLE_STUDENT" - 担任 : "ROLE_TEACHER" - 事務 : "ROLE_STAFF" 必須入力
	 */
	private Role role;

	/**
	 * 所属クラス 固定長文字列(4)
	 */
	private String classroom;

	/**
	 * 出席番号 固定長文字列(2)
	 */
	private String class_number;

	/**
	 * 登録日時 登録時に取得したタイムスタンプ（日時をミリ秒まで）
	 */
	private Date register_date;

	/**
	 * 登録者のユーザID ユーザマスタのユーザIDを参照
	 */
	private String register_user_id;

	/**
	 * 更新者のユーザID 更新時に取得したタイムスタンプ（日時をミリ秒まで） 更新が行われるまでNULL
	 */
	private Date update_date;

	/**
	 * 更新者のユーザID ユーザマスタのユーザIDを参照 更新が行われるまでNULL
	 */
	private String update_user_id;

	/**
	 * アカウント有効性 - 有効 : true - 無効 : false ユーザ作成時はtrue固定
	 */
	private boolean enabled;

}

enum Role {
	ROLE_STUDENT("学生", "ROLE_STUDENT"), ROLE_TEACHER("担任", "ROLE_TEACHER"), ROLE_STAFF("事務", "ROLE_STAFF");

	/** ラベル */
	private String label;

	/** 値 */
	private String value;

	/** コンストラクタ */
	Role(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return this.label;
	}

	public String getValue() {
		return this.value;
	}
}