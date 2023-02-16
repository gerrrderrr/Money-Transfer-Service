package ru.netology.transferservice.validation;

import ru.netology.transferservice.dto.CardOperationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardsNumberNotEqualValidator implements ConstraintValidator<CardsNumberNotEqual, CardOperationDTO> {

    @Override
    public void initialize(CardsNumberNotEqual constraintAnnotation) {
    }

    @Override
    public boolean isValid(CardOperationDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return !dto.getCardFromNumber().equals(dto.getCardToNumber());
    }
}