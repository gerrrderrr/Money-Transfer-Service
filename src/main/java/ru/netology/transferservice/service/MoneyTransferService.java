package ru.netology.transferservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.dto.ConfirmationDTO;
import ru.netology.transferservice.exeption.UnableToConfirm;
import ru.netology.transferservice.exeption.UnableToTransfer;
import ru.netology.transferservice.logger.MoneyTransferLogger;
import ru.netology.transferservice.logger.OperationStatus;
import ru.netology.transferservice.model.CardOperation;
import ru.netology.transferservice.model.Money;
import ru.netology.transferservice.repository.MoneyTransferRepository;
import ru.netology.transferservice.response.ResponseSuccess;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepository repository;
    private final AtomicLong id = new AtomicLong();
    SecureRandom random = new SecureRandom();
    private MoneyTransferLogger logger;

    public MoneyTransferService(MoneyTransferRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initFiles() {
        logger = MoneyTransferLogger.getInstance();
        repository.initCardData();
    }

    public ResponseSuccess transfer(CardOperationDTO dto) {
        if (repository.cardsAreValid(dto)) {
            CardOperation operation = mapOperation(dto);
            setAdditionalOperationInformation(operation);
            repository.addOperation(operation);
            logger.log(dto, OperationStatus.WAITING_FOR_CONFIRMATION);
            return new ResponseSuccess(operation.getOperationId());
        } else {
            logger.log(dto, OperationStatus.FAILED);
            throw new UnableToTransfer("Unable to transfer money from card: " +
                    dto.getCardFromNumber() +
                    " to card: " + dto.getCardToNumber());
        }
    }

    private CardOperation mapOperation(CardOperationDTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(dto, CardOperation.class);
    }

    private void setAdditionalOperationInformation(CardOperation operation) {
        assignId(operation);
        calculateTransferFee(operation);
        generateCVC(operation);
    }

    private void assignId(CardOperation operation) {
        long idFromAtomic = id.incrementAndGet();
        String operationId = String.valueOf(idFromAtomic);
        operation.setOperationId(operationId);
    }

    private void calculateTransferFee(CardOperation operation) {
        int amount = operation.getMoney().getValue();
        int fee = amount / 100;
        String currency = operation.getMoney().getCurrency();
        operation.setTransferFee(new Money(fee, currency));
    }

    private void generateCVC(CardOperation operation) {
        final int min = 100000;
        final int max = 999999;
        final int cvc = random.nextInt(max - min) + min;
        operation.setConfirmationCode(String.valueOf(cvc));
    }

    public ResponseSuccess confirmOperation(ConfirmationDTO confirmationDTO) {
        String id = confirmationDTO.getOperationId();
        if (repository.codeIsCorrect(confirmationDTO)) {
            logger.log("Transfer [" + id + "] was confirmed", OperationStatus.SUCCESS);
            repository.deleteOperation(id);
            return new ResponseSuccess(id);
        } else {
            logger.log("Transfer [" + id + "] was denied", OperationStatus.FAILED);
            repository.deleteOperation(id);
            throw new UnableToConfirm("Unable to confirm transfer [" + id + "]");
        }
    }
}