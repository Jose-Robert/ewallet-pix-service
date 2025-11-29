package br.com.pix_service.ewallet.application.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PixWebhookEventRequest implements Serializable {

    @Schema(description = "Identificador único da transação Pix", example = "E2E20251127008B3F2A7E")
    private String endToEndId;

    @Schema(description = "Identificador do evento de webhook", example = "EVT-20240615143000")
    private String eventId;

    @Schema(description = "Tipo do evento de webhook", example = "PAYMENT_RECEIVED")
    private String eventType;

    @Schema(description = "Timestamp de quando o evento ocorreu", example = "2024-06-15T14:30:00Z")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime occurredAt;

    public String getEventId() {
        var pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        var timestamp = LocalDateTime.now().format(pattern);
        eventId = "EVT-" + timestamp;
        return eventId;
    }
}
