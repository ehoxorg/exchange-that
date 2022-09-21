package org.ehox.ExchangeThat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.ehox.ExchangeThat.service.ExchangeRateService.BAD_REQUEST_EXCEPTION;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=BAD_REQUEST_EXCEPTION)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String badRequestException) {
        super(badRequestException);
    }
}
