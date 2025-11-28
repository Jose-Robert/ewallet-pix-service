package br.com.pix_service.ewallet.infrastructure.handler.exceptions;

public class PixTransferSameWalletException extends RuntimeException {
    public PixTransferSameWalletException() {
    }

    public PixTransferSameWalletException(String message) {
        super(message);
    }
}
