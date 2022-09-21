package org.ehox.ExchangeThat.rest;

import org.ehox.ExchangeThat.service.ExchangeRateService;
import org.ehox.ExchangeThat.service.ServerExchangeRateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.ehox.ExchangeThat.service.ExchangeRateService.EXCHANGE_RATE_URL;
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
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, ServerExchangeRateResponse.class, "incorrect", "USD"))
                .thenReturn(new ResponseEntity<>(new ServerExchangeRateResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=incorrect&to=USD")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get Single ExchangeRate Returns Bad Request when called with incorrect to-value")
    public void getSingleExchangeRateReturnsBadRequestIncorrectTo() throws Exception {
        Mockito.when(restTemplate.getForEntity(EXCHANGE_RATE_URL, ServerExchangeRateResponse.class, "EUR", "incorrect"))
                .thenReturn(new ResponseEntity<>(new ServerExchangeRateResponse(), HttpStatus.BAD_REQUEST));
        this.mockMvc.perform(get("/exchange?from=EUR&to=incorrect")).andExpect(status().isBadRequest());
    }
}
