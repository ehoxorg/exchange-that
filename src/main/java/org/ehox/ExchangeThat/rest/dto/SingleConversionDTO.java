package org.ehox.ExchangeThat.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(example = SingleConversionDTO.docExample)
public class SingleConversionDTO {
    private SingleExchangeRateDTO rate;
    private BigDecimal originalCurrencyAmount;
    private BigDecimal newCurrencyAmount;

    public static final String docExample = "{\n" +
            "  \"rate\": {\n" +
            "    \"from\": \"USD\",\n" +
            "    \"to\": \"EUR\",\n" +
            "    \"date\": \"2022-09-22\",\n" +
            "    \"value\": 1.017657\n" +
            "  },\n" +
            "  \"originalCurrencyAmount\": 130.5,\n" +
            "  \"newCurrencyAmount\": 132.8042385\n" +
            "}";
}
