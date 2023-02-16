package ru.netology.transferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.transferservice.exeption.IncorrectInput;
import ru.netology.transferservice.exeption.UnableToConfirm;
import ru.netology.transferservice.exeption.UnableToTransfer;
import ru.netology.transferservice.exeption.UnableToValidate;
import ru.netology.transferservice.response.ResponseError;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnableToValidate.class)
    public ResponseError handleUnableToValidate(UnableToValidate e) {
        return new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectInput.class)
    public ResponseError handleIncorrectInput(IncorrectInput e) {
        return new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnableToTransfer.class)
    public ResponseError handleUnableToTransfer(UnableToTransfer e) {
        return new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnableToConfirm.class)
    public ResponseError handleUnableToConfirm(UnableToConfirm e) {
        return new ResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
