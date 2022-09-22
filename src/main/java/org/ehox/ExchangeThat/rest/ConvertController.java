package org.ehox.ExchangeThat.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ConvertController {

    @GetMapping("/convert")
    public void convertToCurrency(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount){

    }
}
