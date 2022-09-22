package org.ehox.ExchangeThat.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(example = MultipleConversionsDTO.docExample)
public class MultipleConversionsDTO {
    private String baseCurrency;
    private BigDecimal originalCurrencyAmount;
    private List<ConversionValue> conversionValueList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConversionValue {
        private BigDecimal newCurrencyAmount;
        private SingleExchangeRateDTO rate;
    }

    public static final String docExample ="{\n" +
            "  \"baseCurrency\": \"USD\",\n" +
            "  \"originalCurrencyAmount\": 130.5,\n" +
            "  \"conversionValueList\": [\n" +
            "    {\n" +
            "      \"newCurrencyAmount\": 15482.2155435,\n" +
            "      \"rate\": {\n" +
            "        \"from\": \"USD\",\n" +
            "        \"to\": \"ALL\",\n" +
            "        \"date\": \"2022-09-22\",\n" +
            "        \"value\": 118.637667\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"newCurrencyAmount\": 126.0075375,\n" +
            "      \"rate\": {\n" +
            "        \"from\": \"USD\",\n" +
            "        \"to\": \"CHF\",\n" +
            "        \"date\": \"2022-09-22\",\n" +
            "        \"value\": 0.965575\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"newCurrencyAmount\": 132.8042385,\n" +
            "      \"rate\": {\n" +
            "        \"from\": \"USD\",\n" +
            "        \"to\": \"EUR\",\n" +
            "        \"date\": \"2022-09-22\",\n" +
            "        \"value\": 1.017657\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"newCurrencyAmount\": 116.0180235,\n" +
            "      \"rate\": {\n" +
            "        \"from\": \"USD\",\n" +
            "        \"to\": \"GBP\",\n" +
            "        \"date\": \"2022-09-22\",\n" +
            "        \"value\": 0.889027\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
