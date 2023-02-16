package ru.netology.transferservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MoneyNotNullValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoneyNotNull {
    String message() default "Incorrect Amount";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}