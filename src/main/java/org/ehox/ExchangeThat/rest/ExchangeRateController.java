package org.ehox.ExchangeThat.rest;

import lombok.RequiredArgsConstructor;
import org.ehox.ExchangeThat.service.ExchangeRate;
import org.ehox.ExchangeThat.service.ExchangeRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService rateService;

    @GetMapping("/exchange")
    public ExchangeRate getExchangeRate(@RequestParam String from, @RequestParam String to){
        return rateService.getExchangeRate(from, to);
    }
}
