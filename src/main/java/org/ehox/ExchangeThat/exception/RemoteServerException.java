package org.ehox.ExchangeThat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.ehox.ExchangeThat.service.ExchangeRateService.REMOTE_SERVER_EXCEPTION;

@ResponseStatus(value= HttpStatus.BAD_GATEWAY, reason=REMOTE_SERVER_EXCEPTION)
public class RemoteServerException extends RuntimeException {
    public RemoteServerException(String remoteServerException) {
        super(remoteServerException);
    }
}
