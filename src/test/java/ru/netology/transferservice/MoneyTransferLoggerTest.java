package ru.netology.transferservice;

import com.nitorcreations.junit.runners.NestedRunner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.logger.MoneyTransferLogger;
import ru.netology.transferservice.logger.OperationStatus;
import ru.netology.transferservice.model.Money;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RunWith(NestedRunner.class)
public class MoneyTransferLoggerTest {
    private static final String uri = "src/main/resources/file.log";
    private static final Path path = Path.of(uri);

    private static MoneyTransferLogger logger;

    @Test
    public void returnsLogger() {
        logger = MoneyTransferLogger.getInstance();
        Assertions.assertThat(logger).isNotNull();
    }

    @Nested
    class LoggerCantBeInitialisedAgainTest {
        @Test
        public void returnsSameLogger() {
            MoneyTransferLogger secondLogger = MoneyTransferLogger.getInstance();
            Assertions.assertThat(secondLogger).isSameAs(logger);
        }
    }

    @Nested
    class FileTest {
        @Test
        public void createFile() {
            Assertions.assertThat(Files.exists(path)).isTrue();
        }

        @Nested
        class LoggerTest {
            @AfterEach
            public void deleteFileLog() throws IOException {
                Files.delete(path);
            }

            @Test
            public void doLog() throws IOException {
                CardOperationDTO firstOperation = new CardOperationDTO("firstCardFromNumber",
                        "firstCardFromValidTill",
                        "firstCardFromCVV",
                        "firstCardToNumber",
                        new Money(1000, "firstCurrency"));
                CardOperationDTO secondOperation = new CardOperationDTO("secondCardFromNumber",
                        "secondCardFromValidTill",
                        "secondCardFromCVV",
                        "secondCardToNumber",
                        new Money(2000, "secondCurrency"));

                logger.log(firstOperation, OperationStatus.SUCCESS);
                logger.log(secondOperation, OperationStatus.SUCCESS);

                String actual = Files.readString(path);
                String date = "dd.MM.yyyy / HH:mm";
                int startIndex = date.length() + 1;
                List<String> messages = Arrays.asList(actual.split("\n"));

                String firstExpected = "| STATUS: SUCCESS | Transfer in the amount of 1000 firstCurrency " +
                        "from card firstCardFromNumber to card firstCardToNumber. " +
                        "The transfer fee: 10 firstCurrency.";
                String secondExpected = "| STATUS: SUCCESS | Transfer in the amount of 2000 secondCurrency " +
                        "from card secondCardFromNumber to card secondCardToNumber. " +
                        "The transfer fee: 20 secondCurrency.";

                String firstActual = messages.get(0).substring(startIndex);
                String secondActual = messages.get(1).substring(startIndex);

                Assertions.assertThat(firstActual).isEqualTo(firstExpected);
                Assertions.assertThat(secondActual).isEqualTo(secondExpected);
            }
        }
    }
}