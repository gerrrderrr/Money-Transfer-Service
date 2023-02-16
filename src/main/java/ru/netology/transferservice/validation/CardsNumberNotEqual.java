package ru.netology.transferservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CardsNumberNotEqualValidator.class)
@Target({ElementType.TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardsNumberNotEqual {

    String message() default "Card FROM number could not be equal to card TO number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}