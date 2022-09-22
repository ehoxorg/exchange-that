package org.ehox.ExchangeThat.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(example = SingleConversionDTO.docExample)
public class SingleExchangeRateDTO {
    private String from;
    private String to;
    private String date;
    private Double value;

    public static final String docExample = "{\n" +
            "  \"from\": \"USD\",\n" +
            "  \"to\": \"EUR\",\n" +
            "  \"date\": \"2022-09-22\",\n" +
            "  \"value\": 1.017657\n" +
            "}";
}
