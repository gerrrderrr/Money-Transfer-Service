package ru.netology.transferservice.logger;

import ru.netology.transferservice.dto.CardOperationDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MoneyTransferLogger {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy / HH:mm");
    private static final String uri = "src/main/resources/file.log";
    private static MoneyTransferLogger logger;

    private MoneyTransferLogger() {
    }

    public void log(String msg, OperationStatus status) {
        String statusPart = getStatusPart(status);
        String message = formMessage(msg, statusPart);
        writeMessageToFile(message);
    }

    public void log(CardOperationDTO dto, OperationStatus status) {
        String operationPart = parseOperation(dto);
        String statusPart = getStatusPart(status);
        String message = formMessage(operationPart, statusPart);
        writeMessageToFile(message);
    }

    private String parseOperation(CardOperationDTO dto) {
        String cardFromNumber = dto.getCardFromNumber();
        String cardToNumber = dto.getCardToNumber();
        int moneyAmount = dto.getMoney().getValue();
        int feeAmount = moneyAmount / 100;
        String currency = dto.getMoney().getCurrency();
        return "Transfer in the amount of " + moneyAmount + " " + currency +
                " from card " + cardFromNumber + " to card " + cardToNumber + ". " +
                "The transfer fee: " + feeAmount + " " + currency + ".";
    }

    private String getStatusPart(OperationStatus status) {
        String statusPart;
        switch (status) {
            case SUCCESS:
                statusPart = "| STATUS: SUCCESS |";
                break;
            case FAILED:
                statusPart = "| STATUS: FAILED |";
                break;
            case WAITING_FOR_CONFIRMATION:
                statusPart = "| STATUS: WAITING FOR CONFIRMATION |";
                break;
            default:
                statusPart = "| STATUS: UNKNOWN |";
        }
        return statusPart;
    }

    private String formMessage(String operationPart, String statusPart) {
        return LocalDateTime.now().format(format) + " " +
                statusPart + " " + operationPart + "\n";
    }

    private void writeMessageToFile(String message) {
        try {
            Files.write(getPath(), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static MoneyTransferLogger getInstance() {
        return isAlreadyInitialised() ? logger : createLogger();
    }

    private static boolean isAlreadyInitialised() {
        return logger != null;
    }

    private static MoneyTransferLogger createLogger() {
        logger = new MoneyTransferLogger();
        createLogFile();
        return logger;
    }

    private static void createLogFile() {
        if (noLogFileInDirectory()) {
            createLogFileInDirectory();
        }
    }

    private static boolean noLogFileInDirectory() {
        return Files.notExists(getPath());
    }

    private static Path getPath() {
        return Path.of(uri);
    }

    private static void createLogFileInDirectory() {
        try {
            Files.createFile(getPath());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
