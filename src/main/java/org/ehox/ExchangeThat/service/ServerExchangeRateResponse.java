package org.ehox.ExchangeThat.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ServerExchangeRateResponse {
    private boolean success;
    private String date;
    private InfoResponse info;
    private QueryResponse query;

    @NoArgsConstructor
    @Data
    public static class InfoResponse {
        private double rate;
    }

    @NoArgsConstructor
    @Data
    public static class QueryResponse {
        private String from;
        private String to;
        private int amount;
    }
}


