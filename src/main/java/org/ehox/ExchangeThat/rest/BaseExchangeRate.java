package org.ehox.ExchangeThat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(example = "{\n" +
        "  \"base\": \"USD\",\n" +
        "  \"date\": \"2022-09-22\",\n" +
        "  \"rates\": {\n" +
        "    \"AED\": 3.67043,\n" +
        "    \"AFN\": 89.33442,\n" +
        "    \"ALL\": 118.637665,\n" +
        "    \"AMD\": 419.81934\n}\n}")
public class BaseExchangeRate {
    private String base;
    private String date;
    private Map<String, Float> rates;
}
