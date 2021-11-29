package jp.ac.hcs.GreenShower.job.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 日付の値が妥当か検証する注釈
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = { DateValueValidImp.class })
public @interface DateValueValid {
	
	/**
	 * メッセージを返却する
	 * @return エラーメッセージor空文字
	 */
	String message() default "";
	
	/**
	 * 何かを返却する
	 * @return 何かのクラスの配列or空の配列
	 */
	Class<?>[] groups() default {};
	
	/**
	 * Payload又はそれを継承したクラスの配列を返却する
	 * @return Payload又はそれを継承したクラスの配列or空の配列
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * @see DateValueValid
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		
		/**
		 * 何かの内容を取得・返却する
		 * @return 文字列型の配列
		 */
		String[] value();
	}
}
