package ru.netology.transferservice.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.OK)
public class ResponseSuccess {
    private final String operationId;

    public ResponseSuccess(String operationId) {
        this.operationId = operationId;
    }
}