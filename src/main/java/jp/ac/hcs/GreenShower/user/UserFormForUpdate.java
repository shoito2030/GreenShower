package jp.ac.hcs.GreenShower.user;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * アップデート用にパスワード、ダークモード、権限のチェックを外したUserForm その他の内容はUserFormに準じる
 */
@Data
public class UserFormForUpdate {

	/** ユーザID（メールアドレス） */
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	/** パスワード */
	@Length(min = 4, max = 100)
	@Pattern(regexp = "/^[0-9a-zA-Z]*$/	")
	private String encrypted_password;

	/** ユーザ名 */
	@NotBlank(message = "{require_check}")
	private String name;

	private boolean dark_mode;

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
