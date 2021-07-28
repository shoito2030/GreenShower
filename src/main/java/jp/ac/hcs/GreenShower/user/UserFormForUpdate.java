package jp.ac.hcs.GreenShower.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * アップデート用のUserForm
 */
@Data
public class UserFormForUpdate {
	
	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	/** ユーザ名 */
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
	@Length(min = 0, max = 4, message = "{length_check}")
	private String classroom;

	/**
	 * 出席番号 固定長文字列(2)
	 */
	@Length(min = 0, max = 2, message = "{length_check}")
	private String class_number;

	/**
	 * アカウント有効性 - 有効 : true - 無効 : false ユーザ作成時はtrue固定
	 */
	private boolean enabled;

}
