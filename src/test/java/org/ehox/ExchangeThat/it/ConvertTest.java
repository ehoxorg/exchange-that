package org.ehox.ExchangeThat.it;

import org.ehox.ExchangeThat.rest.dto.MultipleConversionsDTO;
import org.ehox.ExchangeThat.rest.dto.SingleConversionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConvertTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Get Single Conversion is Successful")
    public void getSingleConversionSuccess() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/convert?from=EUR&to=USD&amount=100.05",
                SingleConversionDTO.class);
        assertEquals(LocalDate.now().toString(), result.getRate().getDate());
        assertEquals("EUR", result.getRate().getFrom());
        assertEquals("USD", result.getRate().getTo());
        var fxRate = result.getRate().getValue();
        assertNotNull(fxRate);
        var newAmount = result.getOriginalCurrencyAmount().multiply(BigDecimal.valueOf(fxRate));
        assertEquals(newAmount, result.getNewCurrencyAmount());
    }

    @Test
    @DisplayName("Get Multiple Conversions is Successful")
    public void getMultipleConversionSuccess() {
        var result = this.restTemplate.getForObject("http://localhost:" + port + "/convert-multiple?from=EUR&to=USD&to=ALL&amount=100.05",
                MultipleConversionsDTO.class);
        var conList = result.getConversionValueList();
        assertEquals("EUR", result.getBaseCurrency());
        assertEquals(new BigDecimal("100.05"), result.getOriginalCurrencyAmount());
        for(var conV : conList){
            var fxRate = conV.getRate().getValue();
            assertNotNull(fxRate);
            var newAmount = result.getOriginalCurrencyAmount().multiply(BigDecimal.valueOf(fxRate));
            assertEquals(newAmount, conV.getNewCurrencyAmount());
        }
    }
}
