package br.com.pix_service.ewallet.domain.service;

import br.com.pix_service.ewallet.application.api.request.PixTransferRequest;
import br.com.pix_service.ewallet.application.api.request.PixWebhookEventRequest;
import br.com.pix_service.ewallet.application.api.response.PixTransferResponse;

public interface IPixTransferService {

    PixTransferResponse transferPix(PixTransferRequest pixTransferRequest, String idempotencyKey);
    void executeWebhook(PixWebhookEventRequest webhookEventRequest);
}
