package org.ehox.ExchangeThat.rest;

import org.ehox.ExchangeThat.service.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/exchange?from=EUR&to=USD",
                ExchangeRate.class);
        assertEquals("EUR", result.getFrom());
        assertEquals("USD", result.getTo());
        assertNotNull(result.getValue());
    }
}
