package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Money {
    @NotNull
    @NotBlank
    private int value;
    private String currency;

    public Money(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money() {
    }
}
