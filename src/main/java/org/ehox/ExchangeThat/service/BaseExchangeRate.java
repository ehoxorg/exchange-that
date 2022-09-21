package org.ehox.ExchangeThat.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class BaseExchangeRate {
    private String base;
    private String date;
    private Map<String, Float> rates;
}
