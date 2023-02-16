package ru.netology.transferservice.exeption;

public class UnableToTransfer extends RuntimeException {
    public UnableToTransfer(String message) {
        super(message);
    }
}
