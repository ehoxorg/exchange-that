package org.ehox.ExchangeThat.rest;

import lombok.RequiredArgsConstructor;
import org.ehox.ExchangeThat.service.BaseExchangeRate;
import org.ehox.ExchangeThat.service.ExchangeRateService;
import org.ehox.ExchangeThat.service.SingleExchangeRate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService rateService;

    @GetMapping("/exchange")
    public SingleExchangeRate getExchangeRate(@RequestParam String from, @RequestParam String to){
        return rateService.getSingleExchangeRate(from, to);
    }

    @GetMapping("/exchange/{base}")
    public BaseExchangeRate getExchangeRate(@PathVariable("base") String base){
        return rateService.getBaseExchangeRate(base);
    }
}
