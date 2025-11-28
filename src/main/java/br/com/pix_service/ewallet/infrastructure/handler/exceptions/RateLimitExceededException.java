package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }

}
