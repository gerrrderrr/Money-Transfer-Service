package ru.netology.transferservice.validation;

import ru.netology.transferservice.model.Money;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyNotNullValidator implements ConstraintValidator<MoneyNotNull, Money> {
    @Override
    public void initialize(MoneyNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        return money.getValue() != 0;
    }
}