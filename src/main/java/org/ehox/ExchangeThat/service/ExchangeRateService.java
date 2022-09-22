package org.ehox.ExchangeThat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehox.ExchangeThat.exception.BadRequestException;
import org.ehox.ExchangeThat.exception.RemoteServerException;
import org.ehox.ExchangeThat.rest.dto.BaseExchangeRateDTO;
import org.ehox.ExchangeThat.rest.dto.SingleExchangeRateDTO;
import org.ehox.ExchangeThat.service.response.ExchangeRateRemoteResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {
    public static final String EXCHANGE_RATE_URL = "https://api.exchangerate.host/convert?from={from}&to={to}";
    public static final String BASE_EXCHANGE_RATE_URL = "https://api.exchangerate.host/latest?base={base}";
    public static final String BAD_REQUEST_EXCEPTION = "Call to remote server was a bad request 400";
    public static final String REMOTE_SERVER_EXCEPTION = "Exchange rate server returned server error 500";
    private final RestTemplate restTemplate;

    /**
     * Gets single Exchange Rate for two currencies.
     * @param from From currency
     * @param to To currency
     * @return {@link SingleExchangeRateDTO} instance result
     */
    @Cacheable(value = "exchange_rate_cache", key = "{#from, #to}")
    public SingleExchangeRateDTO getSingleExchangeRate(@NonNull String from, @NonNull String to) {
        var result = restTemplate.getForEntity(EXCHANGE_RATE_URL, ExchangeRateRemoteResponse.class, from, to);
        validateResponse(result);
        return toExchangeRate(from, to, result);
    }

    /**
     * Gets base Exchange Rate from a base currency to all available currencies.
     * @param base  the base currency
     * @return  {@link BaseExchangeRateDTO} instance result
     */
    @Cacheable(value = "exchange_rate_cache", key = "{#base}")
    public BaseExchangeRateDTO getBaseExchangeRate(@NonNull String base){
        var result = restTemplate.getForEntity(BASE_EXCHANGE_RATE_URL, BaseExchangeRateDTO.class, base);
        validateResponse(result);
        return result.getBody();
    }

    private SingleExchangeRateDTO toExchangeRate(String from, String to, ResponseEntity<ExchangeRateRemoteResponse> result) {
        var responseBody = result.getBody();
        boolean fromEquals = responseBody.getQuery().getFrom().equals(from);
        boolean toEquals = responseBody.getQuery().getTo().equals(to);
        if (!fromEquals || !toEquals) {
            throw new IllegalStateException("From or To variables not matching!");
        }
        if(!responseBody.isSuccess()) {
            throw new IllegalStateException("Response was not successful!");
        }
        return SingleExchangeRateDTO.builder()
                .value(responseBody.getInfo().getRate())
                .date(responseBody.getDate())
                .from(from)
                .to(to)
                .build();
    }

    private void validateResponse(ResponseEntity<?> result) {
        if(result.getStatusCode().is5xxServerError()) {
            logAndThrow(REMOTE_SERVER_EXCEPTION, RemoteServerException.class);
        }
        if(result.getStatusCode().is4xxClientError()) {
            logAndThrow(BAD_REQUEST_EXCEPTION, BadRequestException.class);
        }
    }

    private void logAndThrow(String msg, Class<? extends RuntimeException> e) {
        log.error(msg);
        try {
            var cons = e.getConstructor(String.class);
            cons.setAccessible(true);
            throw cons.newInstance(msg);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
