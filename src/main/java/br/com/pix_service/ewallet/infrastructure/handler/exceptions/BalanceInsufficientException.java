package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class BalanceInsufficientException extends RuntimeException {
    public BalanceInsufficientException() {
        super();
    }

    public BalanceInsufficientException(String message) {
        super(message);
    }
}
