package jp.ac.hcs.GreenShower.job.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)

@Constraint(validatedBy = { DateValueValidImp.class })
public @interface DateValueValid {
	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		String[] value();
	}
}
