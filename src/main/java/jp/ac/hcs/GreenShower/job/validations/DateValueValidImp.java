package jp.ac.hcs.GreenShower.job.validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日付の妥当性(受け取った値が今日の日付以降であること)を検証するバリデーション
 *
 */
public class DateValueValidImp implements ConstraintValidator<DateValueValid, String> {

	private static final LocalDateTime NOW = LocalDateTime.now(); 
	
	@Override
    public void initialize(DateValueValid constraintAnnotation) {
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null || value.isEmpty()) {
			return true;
		}
		
		TemporalAccessor parsed;
		
		// 変換不可能な日付文字列の場合はfalse
		try {
			parsed = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(value);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return false;
		}
		
		LocalDateTime someday = LocalDateTime.from(parsed);
		
		// 今日の日付と比較する
		return someday.isAfter(NOW) && someday.getDayOfYear() != NOW.getDayOfYear();
	}

}
