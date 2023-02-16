package ru.netology.transferservice.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ConfirmationDTO {
    @NotBlank
    @NotNull
    String operationId;
    @NotBlank
    @NotNull
    @Length(min = 6, max = 6)
    String code;

    public ConfirmationDTO(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }
}
