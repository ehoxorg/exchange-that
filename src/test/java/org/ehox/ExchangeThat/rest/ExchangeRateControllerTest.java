package org.ehox.ExchangeThat.rest;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.ehox.ExchangeThat.service.ExchangeRateService;
import org.ehox.ExchangeThat.service.RemoteExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.ehox.ExchangeThat.service.ExchangeRateService.EXCHANGE_RATE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ExchangeRateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateService rateService;

    Logger serviceLogger;
    ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setup(){
        serviceLogger = (Logger) LoggerFactory.getLogger(ExchangeRateService.class);
        listAppender = new ListAppender<>();
        listAppender.setName("TEST");
        listAppender.start();
        serviceLogger.addAppender(listAppender);
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Request when called with incorrect from-value")
    public void getSingleExchangeRateReturnsBadRequestIncorrectFrom() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, RemoteExchangeRateResponse.class, "incorrect", "USD"))
                .thenReturn(new ResponseEntity<>(new RemoteExchangeRateResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=incorrect&to=USD")).andExpect(status().isBadRequest());
        assertLogging(ExchangeRateService.BAD_REQUEST_EXCEPTION);
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Request when called with incorrect to-value")
    public void getSingleExchangeRateReturnsBadRequestIncorrectTo() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, RemoteExchangeRateResponse.class, "EUR", "incorrect"))
                .thenReturn(new ResponseEntity<>(new RemoteExchangeRateResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=EUR&to=incorrect")).andExpect(status().isBadRequest());
        assertLogging(ExchangeRateService.BAD_REQUEST_EXCEPTION);
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Gateway when remote server returns 500")
    public void getSingleExchangeRateReturnsBadGatewayIncorrectTo() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, RemoteExchangeRateResponse.class, "EUR", "USD"))
                .thenReturn(new ResponseEntity<>(new RemoteExchangeRateResponse(), HttpStatus.INTERNAL_SERVER_ERROR));
        this.mockMvc.perform(get("/exchange?from=EUR&to=USD")).andExpect(status().isBadGateway());
        assertLogging(ExchangeRateService.REMOTE_SERVER_EXCEPTION);
    }

    private void assertLogging(String badRequestException) {
        List<ILoggingEvent> logsList = this.listAppender.list;
        assertEquals(badRequestException, logsList.get(0)
                .getMessage());
    }
}
