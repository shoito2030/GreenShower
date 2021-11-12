package jp.ac.hcs.GreenShower.job.validations;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidImp implements ConstraintValidator<DateFormatValid, String> {
	private static final String FORMAT = "yyyy/MM/dd HH:mm:ss";

    @Override
    public void initialize(DateFormatValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT);
            dtf.format(LocalDateTime.parse(value, dtf));
            return true;
        } catch(DateTimeException e) {
        	e.printStackTrace();
            return false;
        }
    }
}
