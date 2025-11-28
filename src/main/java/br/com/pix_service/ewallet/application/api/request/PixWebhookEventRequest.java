package br.com.pix_service.ewallet.application.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PixWebhookEventRequest implements Serializable {

    @Schema(description = "Identificador único da transação Pix", example = "E2E202511278B3F2A7E")
    private String endToEndId;

    @Schema(description = "Identificador do evento de webhook", example = "evt_123456789")
    private String eventId;

    @Schema(description = "Tipo do evento de webhook", example = "PAYMENT_RECEIVED")
    private String eventType;

    @Schema(description = "Timestamp de quando o evento ocorreu", example = "2024-06-15T14:30:00Z")
    private String occurredAt;
}
