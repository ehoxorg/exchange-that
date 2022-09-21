package org.ehox.ExchangeThat.exception;

public class RemoteServerException extends RuntimeException {
    public RemoteServerException(String remoteServerException) {
        super(remoteServerException);
    }
}
