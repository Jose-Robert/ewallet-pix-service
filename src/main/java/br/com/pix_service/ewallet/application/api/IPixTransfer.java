package br.com.pix_service.ewallet.application.api;

import br.com.pix_service.ewallet.application.api.request.PixTransferRequest;
import br.com.pix_service.ewallet.application.api.request.PixWebhookEventRequest;
import br.com.pix_service.ewallet.application.api.response.PixTransferResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface IPixTransfer {

    @Operation(summary = "Pix Transfer", description = "Initiating a Pix transfer between wallets.")
    @PostMapping(path = "/pix/transfers")
    ResponseEntity<PixTransferResponse> transferPix(@RequestBody PixTransferRequest pixTransferRequest, @RequestHeader(name = "Idempotency-Key") String idempotencyKey);

    @Operation(summary = "Pix Webhook", description = "Handling Pix webhook events.")
    @PostMapping(path = "/pix/webhook")
    ResponseEntity<Void> processWebhook(@RequestBody PixWebhookEventRequest pixWebhookEventRequest);
}
