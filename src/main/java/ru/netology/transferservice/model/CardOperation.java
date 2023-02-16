package ru.netology.transferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardOperation {
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVC;
    private String cardToNumber;
    private Money money;
    private String operationId;
    private Money transferFee;
    private String confirmationCode;

    public CardOperation(String cardFromNumber,
                         String cardFromValidTill,
                         String cardFromCVC,
                         String cardToNumber,
                         Money money) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVC = cardFromCVC;
        this.cardToNumber = cardToNumber;
        this.money = money;
    }

    public CardOperation() {
    }
}
