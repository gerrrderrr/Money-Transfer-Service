package ru.netology.transferservice.exeption;

public class IncorrectInput extends RuntimeException {
    public IncorrectInput(String message) {
        super(message);
    }
}