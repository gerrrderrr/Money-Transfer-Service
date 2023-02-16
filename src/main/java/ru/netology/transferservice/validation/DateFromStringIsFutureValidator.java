package ru.netology.transferservice.validation;

import ru.netology.transferservice.exeption.IncorrectInput;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateFromStringIsFutureValidator implements ConstraintValidator<DateFromStringIsFuture, String> {
    @Override
    public void initialize(DateFromStringIsFuture dateFromStringIsFuture) {
    }

    @Override
    public boolean isValid(String dateField, ConstraintValidatorContext constraintValidatorContext) {
        int firstDayOfMonth = 1;
        int month = getMonth(dateField);
        int year = getYear(dateField);
        return LocalDate.of(year, month, firstDayOfMonth).isAfter(LocalDate.now());
    }

    private Integer getMonth(String dateField) {
        int separationIndex = 2;
        int month = Integer.parseInt(dateField.substring(0, separationIndex));
        if (month > 0 && month <= 12) {
            return month;
        } else {
            throw new IncorrectInput("Incorrect month entered");
        }
    }

    private Integer getYear(String dateField) {
        String firstDigitsOfAYear = "20";
        int separationIndex = 3;
        return Integer.parseInt(firstDigitsOfAYear + dateField.substring(separationIndex));
    }
}