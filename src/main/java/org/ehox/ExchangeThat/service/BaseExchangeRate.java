package org.ehox.ExchangeThat.service;

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
public class BaseExchangeRate {
    private String base;
    private String date;
    private Map<String, Float> rates;
}
