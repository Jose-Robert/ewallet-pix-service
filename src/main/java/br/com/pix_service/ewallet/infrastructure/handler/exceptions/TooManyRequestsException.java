package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }

}
