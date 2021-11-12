package jp.ac.hcs.GreenShower.job.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

import org.springframework.format.annotation.DateTimeFormat;

@Retention(RUNTIME)
@Target(FIELD)
public @interface DateFormatValid {
	String message() default "{0}は日付時刻として許可された形式ではありません。";
	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};

	  @Target({ FIELD })
	  @Retention(RUNTIME)
	  @Documented
	  public @interface List {
	     DateTimeFormat[] value();
	  }
}
