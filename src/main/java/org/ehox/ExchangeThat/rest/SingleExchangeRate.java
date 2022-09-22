package org.ehox.ExchangeThat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(example = "{\n" +
        "  \"from\": \"USD\",\n" +
        "  \"to\": \"EUR\",\n" +
        "  \"date\": \"2022-09-22\",\n" +
        "  \"value\": 1.017657\n" +
        "}")
public class SingleExchangeRate {
    private String from;
    private  String to;
    private String date;
    private  Double value;
}
