package ru.netology.transferservice;

import com.nitorcreations.junit.runners.NestedRunner;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.model.CardOperation;
import ru.netology.transferservice.model.Money;
import ru.netology.transferservice.repository.MoneyTransferRepository;
import ru.netology.transferservice.service.MoneyTransferService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@RunWith(NestedRunner.class)
public class MoneyTransferRepositoryTest {
    private final String firstId = "1";
    private final String secondId = "2";
    private MoneyTransferRepository repository;
    private CardOperation firstOperation;
    private CardOperation secondOperation;

    @Before
    public void init() {
        repository = new MoneyTransferRepository();
        MoneyTransferService service = new MoneyTransferService(repository);
        service.initFiles();
        String classVariable = "Some Value";
        firstOperation = new CardOperation(classVariable, classVariable, classVariable, classVariable, Mockito.mock(Money.class));
        secondOperation = new CardOperation(classVariable, classVariable, classVariable, classVariable, Mockito.mock(Money.class));
        firstOperation.setOperationId(firstId);
        secondOperation.setOperationId(secondId);
        repository.addOperation(firstOperation);
        repository.addOperation(secondOperation);
    }

    @AfterClass
    public static void deleteFileLog() throws IOException {
        Files.delete(Path.of("src/main/resources/file.log"));
    }

    @Test
    public void getOperationTest() {
        CardOperation actualForFirst = repository.getOperation(firstId);
        CardOperation actualForSecond = repository.getOperation(secondId);

        Assertions.assertThat(actualForFirst).isEqualTo(firstOperation);
        Assertions.assertThat(actualForSecond).isEqualTo(secondOperation);
    }

    @Test
    public void isValid() {
        CardOperationDTO dto = new CardOperationDTO("1111111111111111",
                "01/24", "111",
                "2222222222222222",
                new Money(200, "Rub"));
        Assertions.assertThat(repository.cardsAreValid(dto)).isTrue();
    }

    public static class TestWithMockRepository {
        @Test
        public void addOperationTest() {
            MoneyTransferRepository repositoryMock = mock(MoneyTransferRepository.class);
            CardOperation operationMock = mock(CardOperation.class);
            doNothing().when(repositoryMock).addOperation(operationMock);
            repositoryMock.addOperation(operationMock);
            Mockito.verify(repositoryMock, only()).addOperation(operationMock);
        }
    }
}
