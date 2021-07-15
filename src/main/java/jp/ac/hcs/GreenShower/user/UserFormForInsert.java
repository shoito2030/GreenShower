package jp.ac.hcs.GreenShower.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 1件分のユーザ情報 各項目のデータ仕様も合わせて管理する
 */
@Data
public class UserFormForInsert {
	/**
	 * ユーザID(メールアドレス) 主キー、必須入力、メールアドレス形式
	 */
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	/**
	 * パスワード 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	@NotBlank(message = "{require_check}")
	@Length(min = 4, max = 100, message = "{length_check}")
	@Pattern(regexp = "[0-9a-zA-Z]+", message = "{pattern_check}")
	private String encrypted_password;

	/**
	 * ユーザ名 必須入力
	 */
	@NotBlank(message = "{require_check}")
	private String name;

	/**
	 * 権限 - 学生 : "ROLE_STUDENT" - 担任 : "ROLE_TEACHER" - 事務 : "ROLE_STAFF" 必須入力
	 */
	@NotBlank(message = "{require_check}")
	private String role;

	/**
	 * 所属クラス 固定長文字列(4)
	 */
	@Length(min = 4, max = 4, message = "{length_check}")
	private String classroom;

	/**
	 * 出席番号 固定長文字列(2)
	 */
	@Length(min = 2, max = 2, message = "{length_check}")
	private String class_number;
}