package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() {
    }

    public InvalidArgumentException(String message) {
        super(message);
    }
}
