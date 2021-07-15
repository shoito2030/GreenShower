package jp.ac.hcs.GreenShower.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * アップデート用にパスワード、ダークモード、権限のチェックを外したUserForm その他の内容はUserFormに準じる
 */
@Data
public class UserFormForUpdate {

	/** ユーザ名 */
	@NotBlank(message = "{require_check}")
	private String name;
	
	/**
	 * 権限 - 学生 : "ROLE_STUDENT" - 担任 : "ROLE_TEACHER" - 事務 : "ROLE_STAFF" 必須入力
	 */
	private String role;

	/**
	 * 所属クラス 固定長文字列(4)
	 */
	private String classroom;

	/**
	 * 出席番号 固定長文字列(2)
	 */
	private String class_number;

	/**
	 * アカウント有効性 - 有効 : true - 無効 : false ユーザ作成時はtrue固定
	 */
	private boolean enabled;

}
