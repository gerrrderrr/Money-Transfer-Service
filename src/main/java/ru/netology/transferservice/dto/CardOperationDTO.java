package ru.netology.transferservice.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.netology.transferservice.model.Money;
import ru.netology.transferservice.validation.CardsNumberNotEqual;
import ru.netology.transferservice.validation.DateFromStringIsFuture;
import ru.netology.transferservice.validation.MoneyNotNull;

import javax.validation.constraints.NotBlank;

@Getter
@CardsNumberNotEqual
public class CardOperationDTO {
    @NotBlank
    @Length(min = 16, max = 16)
    String cardFromNumber;
    @NotBlank
    @Length(min = 5, max = 5)
    @DateFromStringIsFuture
    String cardFromValidTill;
    @NotBlank
    @Length(min = 3, max = 3)
    String cardFromCVC;
    @NotBlank
    @Length(min = 16, max = 16)
    String cardToNumber;
    @MoneyNotNull
    Money money;

    public CardOperationDTO(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Money amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVC = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.money = amount;
    }
}
