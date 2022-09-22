package org.ehox.ExchangeThat.service;

import lombok.AllArgsConstructor;
import org.ehox.ExchangeThat.rest.dto.MultipleConversionsDTO;
import org.ehox.ExchangeThat.rest.dto.SingleConversionDTO;
import org.ehox.ExchangeThat.rest.dto.SingleExchangeRateDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConvertService {
    private final ExchangeRateService rateService;

    /**
     * Converts amount of resources from currency A to currency B.
     * @param from from currency value
     * @param to to currency value
     * @param amount amount of resources in original (old) currency
     * @return  instance of {@link SingleConversionDTO}
     */
    public SingleConversionDTO convertToSingleCurrency(String from, String to, BigDecimal amount){
        var singleExchange = rateService.getSingleExchangeRate(from, to);
        BigDecimal convertedValue = getNewCurrencyAmount(amount, singleExchange);
        return SingleConversionDTO.builder()
                .newCurrencyAmount(convertedValue)
                .rate(singleExchange)
                .originalCurrencyAmount(amount)
                .build();
    }

    /**
     * Converts from currency A to a List of currencies and returns the result.
     * @param from from currency
     * @param to List of currencies
     * @param amount amount of resources in currency A
     * @return instance of {@link MultipleConversionsDTO}
     */
    public MultipleConversionsDTO convertToMultipleCurrencies(String from, List<String> to, BigDecimal amount){
        var conversionValueList = to.parallelStream()
                .map(newC -> rateService.getSingleExchangeRate(from, newC))
                .map(exRate -> MultipleConversionsDTO.ConversionValue.builder()
                        .rate(exRate)
                        .newCurrencyAmount(getNewCurrencyAmount(amount, exRate))
                        .build())
                .collect(Collectors.toList());
        return MultipleConversionsDTO.builder()
                .baseCurrency(from)
                .originalCurrencyAmount(amount)
                .conversionValueList(conversionValueList)
                .build();
    }

    private BigDecimal getNewCurrencyAmount(BigDecimal amount, SingleExchangeRateDTO singleExchange) {
        var fxRate = singleExchange.getValue();
        var convertedValue = amount.multiply(BigDecimal.valueOf(fxRate));
        return convertedValue;
    }
}
