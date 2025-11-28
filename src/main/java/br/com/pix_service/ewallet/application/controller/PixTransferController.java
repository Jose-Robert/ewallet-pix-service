package br.com.pix_service.ewallet.application.controller;

import br.com.pix_service.ewallet.application.api.IPixTransfer;
import br.com.pix_service.ewallet.application.api.request.PixTransferRequest;
import br.com.pix_service.ewallet.application.api.request.PixWebhookEventRequest;
import br.com.pix_service.ewallet.application.api.response.PixTransferResponse;
import br.com.pix_service.ewallet.domain.service.IPixTransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PixTransferController implements IPixTransfer {

    private final IPixTransferService service;

    @Override
    public ResponseEntity<PixTransferResponse> transferPix(PixTransferRequest pixTransferRequest, String idempotencyKey) {
        return new ResponseEntity<>(service.transferPix(pixTransferRequest, idempotencyKey),  HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> pixWebhook(PixWebhookEventRequest pixWebhookEventRequest) {
        return ResponseEntity.ok().build();
    }
}
