package ru.netology.transferservice.response;

import lombok.Getter;

@Getter
public class ResponseError {
    private final String message;
    private final int id;

    public ResponseError(String message, int id) {
        this.message = message;
        this.id = id;
    }
}