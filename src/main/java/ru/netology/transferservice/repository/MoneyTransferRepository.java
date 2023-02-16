package ru.netology.transferservice.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.dto.ConfirmationDTO;
import ru.netology.transferservice.model.CardOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MoneyTransferRepository {
    private final Map<String, CardOperation> operations = new ConcurrentHashMap<>();
    private HashMap<String, Map<String, String>> data;
    private static final String dataFileUri = "src/main/resources/cards_data.json";

    public MoneyTransferRepository() {
    }

    public void addOperation(CardOperation operation) {
        String operationId = operation.getOperationId();
        operations.put(operationId, operation);
    }

    public CardOperation getOperation(String operationId) {
        return operations.get(operationId);
    }

    public void deleteOperation(String operationId) {
        operations.remove(operationId);
    }

    public boolean cardsAreValid(CardOperationDTO dto) {
        if (cardExists(dto.getCardFromNumber()) && cardExists(dto.getCardToNumber())) {
            return cardFromIsValid(dto);
        }
        return false;
    }

    private boolean cardFromIsValid(CardOperationDTO dto) {
        String givenCardFromValidTill = dto.getCardFromValidTill();
        String givenCardFromCvc = dto.getCardFromCVC();
        String actualCardFromValidTill = data.get(dto.getCardFromNumber()).get("dateValidTill");
        String actualCardFromCvc = data.get(dto.getCardFromNumber()).get("cvc");
        return givenCardFromValidTill.equals(actualCardFromValidTill) && givenCardFromCvc.equals(actualCardFromCvc);
    }

    private boolean cardExists(String cardNumber) {
        return data.containsKey(cardNumber);
    }

    public boolean codeIsCorrect(ConfirmationDTO dto) {
        String id = dto.getOperationId();
        if (operationExist(id)) {
            return operations.get(id).getConfirmationCode().equals(dto.getCode());
        }
        return false;
    }

    private boolean operationExist(String operationId) {
        return operations.containsKey(operationId);
    }

    public void initCardData() {
        if (data == null) {
            loadCardsData();
        }
    }

    private void loadCardsData() {
        String cardsData = readDataFromFile();
        data = new Gson().fromJson(cardsData,
                new TypeToken<HashMap<String, Map<String, String>>>() {
                }.getType());
    }

    private String readDataFromFile() {
        try {
            Path path = Path.of(dataFileUri);
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}