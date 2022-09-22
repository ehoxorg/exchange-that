package org.ehox.ExchangeThat.it;

import org.ehox.ExchangeThat.rest.BaseExchangeRate;
import org.ehox.ExchangeThat.rest.SingleExchangeRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Get Single ExchangeRate is Successful")
    public void getSingleExchangeRateSuccess() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/exchange?from=EUR&to=USD",
                SingleExchangeRate.class);
        assertEquals(LocalDate.now().toString(), result.getDate());
        assertEquals("EUR", result.getFrom());
        assertEquals("USD", result.getTo());

        assertNotNull(result.getValue());
    }

    @Test
    @DisplayName("Get Base ExchangeRate is Successful")
    public void getBaseExchangeRateSuccess() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/exchange/USD",
                BaseExchangeRate.class);
        assertEquals("USD", result.getBase());
        assertEquals(LocalDate.now().toString(), result.getDate());
        assertNotNull(result.getRates());
    }
}
