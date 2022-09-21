package org.ehox.ExchangeThat.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleExchangeRate {
    private String from;
    private  String to;
    private String date;
    private  Double value;
}
