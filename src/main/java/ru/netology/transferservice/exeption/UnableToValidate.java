package ru.netology.transferservice.exeption;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class UnableToValidate extends MethodArgumentNotValidException {
    public UnableToValidate(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
    }
}
