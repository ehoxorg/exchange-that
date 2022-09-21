package org.ehox.ExchangeThat.rest;

import org.ehox.ExchangeThat.service.BaseExchangeRate;
import org.ehox.ExchangeThat.service.SingleExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getSingleExchangeRate() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/exchange?from=EUR&to=USD",
                SingleExchangeRate.class);
        assertEquals(LocalDate.now().toString(), result.getDate());
        assertEquals("EUR", result.getFrom());
        assertEquals("USD", result.getTo());

        assertNotNull(result.getValue());
    }

    @Test
    public void getBaseExchangeRate() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/exchange/USD",
                BaseExchangeRate.class);
        assertEquals("USD", result.getBase());
        assertEquals(LocalDate.now().toString(), result.getDate());
        assertNotNull(result.getRates());
    }
}
