package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
