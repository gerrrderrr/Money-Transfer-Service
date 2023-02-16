package ru.netology.transferservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.dto.ConfirmationDTO;
import ru.netology.transferservice.response.ResponseSuccess;
import ru.netology.transferservice.service.MoneyTransferService;

import javax.validation.Valid;

@RestController
public class MoneyTransferController {
    private final MoneyTransferService service;

    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public ResponseSuccess transfer(@Valid @RequestBody CardOperationDTO dto) {
        return service.transfer(dto);
    }

    @PostMapping("/confirmOperation")
    public ResponseSuccess confirmOperation(@Valid @RequestBody ConfirmationDTO confirmationDTO) {
        return service.confirmOperation(confirmationDTO);
    }
}
