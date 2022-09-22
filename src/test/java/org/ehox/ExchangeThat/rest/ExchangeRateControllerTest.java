package org.ehox.ExchangeThat.rest;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.ehox.ExchangeThat.service.ExchangeRateService;
import org.ehox.ExchangeThat.service.response.ExchangeRateRemoteResponse;
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


    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Request when called with incorrect from-value")
    public void getSingleExchangeRateReturnsBadRequestIncorrectFrom() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, ExchangeRateRemoteResponse.class, "incorrect", "USD"))
                .thenReturn(new ResponseEntity<>(new ExchangeRateRemoteResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=incorrect&to=USD")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Request when called with incorrect to-value")
    public void getSingleExchangeRateReturnsBadRequestIncorrectTo() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, ExchangeRateRemoteResponse.class, "EUR", "incorrect"))
                .thenReturn(new ResponseEntity<>(new ExchangeRateRemoteResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=EUR&to=incorrect")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Gateway when remote server returns 500")
    public void getSingleExchangeRateReturnsBadGatewayIncorrectTo() throws Exception {
        //before
        Logger serviceLogger = (Logger) LoggerFactory.getLogger(ExchangeRateService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        serviceLogger.addAppender(listAppender);

        //when
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, ExchangeRateRemoteResponse.class, "EUR", "USD"))
                .thenReturn(new ResponseEntity<>(new ExchangeRateRemoteResponse(), HttpStatus.INTERNAL_SERVER_ERROR));
        this.mockMvc.perform(get("/exchange?from=EUR&to=USD")).andExpect(status().isBadGateway());

        //then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(ExchangeRateService.REMOTE_SERVER_EXCEPTION, logsList.get(0)
                .getMessage());

    }
}
