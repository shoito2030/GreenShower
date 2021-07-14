package jp.ac.hcs.GreenShower.user;

import jp.ac.hcs.s3a103.user.Role;
import lombok.Data;

/**
 * 1件分のユーザ情報
 * 各項目のデータ仕様も合わせて管理する
 */
@Data
public class UserData {
	/**
	 * ユーザID(メールアドレス)
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;
	
	/**
	 * パスワード
	 * 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String password;
	
	/**
	 * アカウント有効性
	 * - 有効 : true
	 * - 無効 : false
	 * ユーザ作成時はtrue固定
	 */
	private boolean enabled;
	
	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;
	
	/**
	 * ダークモードフラグ
	 * - ON :true
	 * - OFF:false
	 * ユーザ作成時はfalse固定
	 */
	private boolean darkmode;
	
	/**
	 * 権限
	 * - 学生 : "ROLE_STUDENT"
	 * - 担任 : "ROLE_TEACHER"
	 * - 事務 : "ROLE_STAFF"
	 * 必須入力
	 */
	private Role role;
	
	/**
	 * 所属クラス
	 * 固定長文字列(4)
	 */
	private String class;

}
