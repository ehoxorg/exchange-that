package org.ehox.ExchangeThat.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get a single currency exchange rate for two given currencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got exchange rate from one to another currency",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleExchangeRate.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid from OR to variable value supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Error in remote server",
                    content = @Content)
    })
    @GetMapping("/exchange")
    public SingleExchangeRate getSingleExchangeRate(@RequestParam String from, @RequestParam String to){
        return rateService.getSingleExchangeRate(from, to);
    }

    @Operation(summary = "Get all currency exchange rates for a given currency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all exchange rates for given currency",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseExchangeRate.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid base value supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Error in remote server",
                    content = @Content)
    })
    @GetMapping("/exchange/{base}")
    public BaseExchangeRate getBaseExchangeRate(@PathVariable("base") String base){
        return rateService.getBaseExchangeRate(base);
    }
}
