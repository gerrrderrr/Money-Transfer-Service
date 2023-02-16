package ru.netology.transferservice;

import com.nitorcreations.junit.runners.NestedRunner;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.dto.ConfirmationDTO;
import ru.netology.transferservice.exeption.UnableToConfirm;
import ru.netology.transferservice.model.CardOperation;
import ru.netology.transferservice.model.Money;
import ru.netology.transferservice.repository.MoneyTransferRepository;
import ru.netology.transferservice.response.ResponseSuccess;
import ru.netology.transferservice.service.MoneyTransferService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RunWith(NestedRunner.class)
public class MoneyTransferServiceTest {
    private static MoneyTransferRepository repository;
    private static MoneyTransferService service;
    private static CardOperationDTO firstOperationDTO;
    private static CardOperationDTO secondOperationDTO;
    private static final String uri = "src/main/resources/file.log";
    private static final Path path = Path.of(uri);

    @BeforeAll
    public static void init() throws IOException {
        repository = new MoneyTransferRepository();
        service = new MoneyTransferService(repository);
        Files.createFile(path);
        service.initFiles();
        firstOperationDTO = new CardOperationDTO("1111111111111111",
                "01/24",
                "111",
                "2222222222222222",
                new Money(1000, "firstCurrency"));
        secondOperationDTO = new CardOperationDTO("3333333333333333",
                "03/24",
                "333",
                "4444444444444444",
                new Money(2000, "secondCurrency"));
    }

    @AfterAll
    public static void deleteFileLog() throws IOException {
        Files.delete(path);
    }

    @Test
    public void transferTest() {
        ResponseSuccess firstResponse = service.transfer(firstOperationDTO);
        ResponseSuccess secondResponse = service.transfer(secondOperationDTO);

        Assertions.assertThat(firstResponse).isNotNull();
        Assertions.assertThat(secondResponse).isNotNull();
    }

    @Nested
    class MappingResultTest {

        private CardOperation firstOperation;
        private CardOperation secondOperation;
        private final String firstOperationId = "1";
        private final String secondOperationId = "2";

        @BeforeEach
        public void init() {
            firstOperation = repository.getOperation(firstOperationId);
            secondOperation = repository.getOperation(secondOperationId);
        }

        @Test
        public void operationExist() {
            Assertions.assertThat(firstOperation).isNotNull();
            Assertions.assertThat(secondOperation).isNotNull();
        }

        @Test
        public void operationHasAnId() {
            String firstActual = firstOperation.getOperationId();
            String secondActual = secondOperation.getOperationId();

            Assertions.assertThat(firstActual).isEqualTo(firstOperationId);
            Assertions.assertThat(secondActual).isEqualTo(secondOperationId);
        }

        @Test
        public void checkMoneyAmount() {
            int firstActual = firstOperation.getMoney().getValue();
            int firstExpected = firstOperationDTO.getMoney().getValue();
            int secondActual = secondOperation.getMoney().getValue();
            int secondExpected = secondOperationDTO.getMoney().getValue();

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void transferFeeIsCorrect() {
            int firstActual = firstOperation.getTransferFee().getValue();
            int firstExpected = 10;
            int secondActual = secondOperation.getTransferFee().getValue();
            int secondExpected = 20;

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void fieldCardFromNumberAssignedCorrectly() {
            String firstActual = firstOperation.getCardFromNumber();
            String firstExpected = "1111111111111111";
            String secondActual = secondOperation.getCardFromNumber();
            String secondExpected = "3333333333333333";

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void fieldCardFromValidTillAssignedCorrectly() {
            String firstActual = firstOperation.getCardFromValidTill();
            String firstExpected = "01/24";
            String secondActual = secondOperation.getCardFromValidTill();
            String secondExpected = "03/24";

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void fieldCardFromCVVAssignedCorrectly() {
            String firstActual = firstOperation.getCardFromCVC();
            String firstExpected = "111";
            String secondActual = secondOperation.getCardFromCVC();
            String secondExpected = "333";

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void fieldCardToNumberAssignedCorrectly() {
            String firstActual = firstOperation.getCardToNumber();
            String firstExpected = "2222222222222222";
            String secondActual = secondOperation.getCardToNumber();
            String secondExpected = "4444444444444444";

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Test
        public void fieldCurrencyAssignedCorrectly() {
            String firstActual = firstOperation.getMoney().getCurrency();
            String firstExpected = "firstCurrency";
            String secondActual = secondOperation.getMoney().getCurrency();
            String secondExpected = "secondCurrency";

            Assertions.assertThat(firstActual).isEqualTo(firstExpected);
            Assertions.assertThat(secondActual).isEqualTo(secondExpected);
        }

        @Nested
        public class ConfirmationTest {
            @Test
            public void transferConfirmed() {
                String firstCode = firstOperation.getConfirmationCode();
                String secondCode = secondOperation.getConfirmationCode();

                ConfirmationDTO firstDto = new ConfirmationDTO(firstOperationId, firstCode);
                ConfirmationDTO firstIncorrectDto = new ConfirmationDTO(firstOperationId, "aaaaaa");
                ConfirmationDTO secondDto = new ConfirmationDTO(secondOperationId, secondCode);
                ConfirmationDTO secondIncorrectDto = new ConfirmationDTO("4", secondCode);

                Assertions.assertThat(service.confirmOperation(firstDto))
                        .returns(ResponseSuccess.class, ResponseSuccess::getClass);
                Assertions.assertThat(service.confirmOperation(secondDto))
                        .returns(ResponseSuccess.class, ResponseSuccess::getClass);
                Assert.assertThrows(UnableToConfirm.class, () -> service.confirmOperation(firstIncorrectDto));
                Assert.assertThrows(UnableToConfirm.class, () -> service.confirmOperation(secondIncorrectDto));
            }
        }
    }
}
