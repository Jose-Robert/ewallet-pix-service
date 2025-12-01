package br.com.pix_service.ewallet.builder;

import br.com.pix_service.ewallet.application.api.request.PixTransferRequest;
import br.com.pix_service.ewallet.application.api.request.PixWebhookEventRequest;
import br.com.pix_service.ewallet.domain.dto.TransactionTO;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.enums.KeyType;
import br.com.pix_service.ewallet.domain.enums.StatusType;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PixTransferBuilder {

    public static PixTransferRequest buildPixTransferRequest() {
        return PixTransferRequest.builder()
                .fromWalletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .toPixKey("11912345678")
                .amount(new BigDecimal("220.00"))
                .build();
    }

    public static WalletEntity buildToWalletEntity() {
        return WalletEntity.builder()
                .id(UUID.fromString("d68746eb-de23-407c-ad43-194263d10196"))
                .cpf("12345678900")
                .name("José Robert")
                .email("jrobert@teste.com")
                .balance(new BigDecimal("0.00"))
                .keyType(KeyType.PHONE)
                .pixKey("11912345678")
                .build();
    }


    public static TransactionTO buildTransactionTo() {
        return TransactionTO.builder()
                .id(UUID.fromString("8c55559a-dcbb-4750-9e16-901d3ce97196"))
                .amount(new BigDecimal("220.00"))
                .createdAt(OffsetDateTime.parse("2025-11-29T03:22:55.82Z").toLocalDateTime())
                .endToEndId("E2E202511290022553270FB47")
                .toWalletId("d68746eb-de23-407c-ad43-194263d10196")
                .type(TransactionType.PIX_TRANSFER)
                .walletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .idempotencyKey("71f0efcc-4f89-4e53-9efc-1a786af3edc2")
                .status(StatusType.PENDING)
                .build();
    }

    public static PixWebhookEventRequest buildWebhookRequest() {
        return PixWebhookEventRequest.builder()
                .endToEndId("E2E202511290022553270FB47")
                .eventId("EVT-20251129004616")
                .eventType("CONFIRMED")
                .occurredAt(OffsetDateTime.parse("2025-11-29T03:22:55.82Z").toLocalDateTime())
                .build();
    }
}
