package ru.netology.transferservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.netology.transferservice.controller.MoneyTransferController;
import ru.netology.transferservice.dto.CardOperationDTO;
import ru.netology.transferservice.model.Money;
import ru.netology.transferservice.response.ResponseSuccess;
import ru.netology.transferservice.service.MoneyTransferService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MoneyTransferController.class)
public class TransferValidationTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    static MoneyTransferService service;

    @BeforeAll
    public static void setUp() {
        ResponseSuccess response = new ResponseSuccess("1");
        Mockito.when(service.transfer(any())).thenReturn(response);
    }

    @Test
    public void transfer() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/transfer")
                        .content(new ObjectMapper().writeValueAsString(new CardOperationDTO(
                                "1111111111111111",
                                "01/24",
                                "111",
                                "2222222222222222",
                                new Money(1000, "firstCurrency"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
