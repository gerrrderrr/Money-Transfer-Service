package ru.netology.transferservice.exeption;

public class UnableToConfirm extends RuntimeException {
    public UnableToConfirm(String message) {
        super(message);
    }
}
