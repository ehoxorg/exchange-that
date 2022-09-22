package org.ehox.ExchangeThat.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.ehox.ExchangeThat.rest.dto.MultipleConversionsDTO;
import org.ehox.ExchangeThat.rest.dto.SingleConversionDTO;
import org.ehox.ExchangeThat.service.ConvertService;
import org.ehox.ExchangeThat.service.CurrencyCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConvertController {
    private final ConvertService convertService;

    @Operation(summary = "Convert an amount from one currency to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got conversion details for the given currencies and amount",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleConversionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid base value supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Error in remote server",
                    content = @Content),
    })
    @GetMapping("/convert")
    public SingleConversionDTO convertToCurrency(@RequestParam @Parameter(example = "USD") CurrencyCode from, @RequestParam @Parameter(example = "EUR") CurrencyCode to, @RequestParam @Parameter(example = "130.5") BigDecimal amount){
        return convertService.convertToSingleCurrency(from.toString(), to.toString(), amount);
    }

    @Operation(summary = "Convert an amount from one currency to a list of others")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got conversion details for the given currencies and amounts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MultipleConversionsDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid base value supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Error in remote server",
                    content = @Content),
    })
    @GetMapping("/convert-multiple")
    public MultipleConversionsDTO convertToMultipleCurrencies(@RequestParam @Parameter(example = "USD") CurrencyCode from, @RequestParam @Parameter(example = "EUR\nUSD\n...") List<CurrencyCode> to, @RequestParam @Parameter(example = "130.5") BigDecimal amount) {
        return convertService.convertToMultipleCurrencies(from.toString(), CurrencyCode.toStringList(to), amount);
    }
}
