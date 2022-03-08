package ie.dublinanalytica.web.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that the annotated String field is a well-formed email address.
 * For use with Spring's @Validated annotation.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidEmail.EmailValidator.class)
@Documented
public @interface ValidEmail {
  /**
   * Required by @Constraint.
   */
  String message() default "Invalid email address";

  /**
   * Required by @Constraint.
   */
  Class<?>[] groups() default {};

  /**
   * Required by @Constraint.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Email validator class for field validation.
   */
  class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN_OBJECT = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
      return EmailValidator.validateEmail(email);
    }

    private static boolean validateEmail(String email) {
      Matcher matcher = EMAIL_PATTERN_OBJECT.matcher(email);
      return matcher.matches();
    }
  }
}
