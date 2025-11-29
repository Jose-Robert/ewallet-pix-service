package br.com.pix_service.ewallet.infrastructure.handler;

import br.com.pix_service.ewallet.infrastructure.component.MessageComponent;
import br.com.pix_service.ewallet.infrastructure.handler.exceptions.*;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageComponent message;

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> handlerObjectNotFoundException(ObjectNotFoundException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.NOT_FOUND, request, "OBJECT.NOT-FOUND", args);
    }

    @ExceptionHandler({InvalidArgumentException.class})
    public ResponseEntity<Object> handlerInvalidArgumentException(InvalidArgumentException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "ERROR.BAD-REQUEST", args);
    }

    @ExceptionHandler({BalanceInsufficientException.class})
    public ResponseEntity<Object> handlerBalanceInsufficientException(BalanceInsufficientException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.CONFLICT, request, "BALANCE.INSUFFICIENT", args);
    }

    @ExceptionHandler({InvalidAmountException.class})
    public ResponseEntity<Object> handlerInvalidAmountException(InvalidAmountException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.CONFLICT, request, "INVALID_AMOUNT", args);
    }

    @ExceptionHandler({PixTransferSameWalletException.class})
    public ResponseEntity<Object> handlerPixTransferSameWalletException(PixTransferSameWalletException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.UNPROCESSABLE_ENTITY, request, "CANNOT.TRANSFER.SAME-WALLET", args);
    }

    @ExceptionHandler({RateLimitExceededException.class})
    public ResponseEntity<Object> handlerRateLimitExceededException(RateLimitExceededException exception, WebRequest request) {
        Object[] args = {exception.getMessage()};
        return handlerException(exception, HttpStatus.TOO_MANY_REQUESTS, request, "RATE_LIMIT_EXCEEDED", args);
    }

    private ResponseEntity<Object> handlerException(Exception exception, HttpStatus status, WebRequest request, String key, Object[] args) {
        ApiError<List<String>> response = new ApiError<>(List.of((message.getMessage(key, args))));
        response.setStatusCode(status.value());
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

}
