package org.ehox.ExchangeThat.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String badRequestException) {
        super(badRequestException);
    }
}
