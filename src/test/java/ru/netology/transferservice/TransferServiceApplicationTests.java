//package ru.netology.transferservice;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import ru.netology.transferservice.dto.CardOperationDTO;
//import ru.netology.transferservice.model.Money;
//import ru.netology.transferservice.response.ResponseSuccess;
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class TransferServiceApplicationTests {
//
//    private final int PORT = 5500;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Container
//    private final GenericContainer<?> testAppFirst = new GenericContainer<>("server")
//            .withExposedPorts(PORT);
//
//    @Test
//    void contextLoads() {
//        Integer appPort = testAppFirst.getMappedPort(PORT);
//        ResponseSuccess response = restTemplate
//                .getForEntity("http://localhost:" + appPort + "/transfer",
//                        ResponseSuccess.class,
//                        new CardOperationDTO(
//                                "1111111111111111",
//                                "0124",
//                                "111",
//                                "2222222222222222",
//                                new Money(1000, "firstCurrency"))).getBody();
//        System.out.println(response);
//    }
//}
