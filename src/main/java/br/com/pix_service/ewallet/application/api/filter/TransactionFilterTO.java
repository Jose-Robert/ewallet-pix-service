package br.com.pix_service.ewallet.application.api.filter;

import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import br.com.pix_service.ewallet.infrastructure.annotation.specification.SpecificationEntity;
import br.com.pix_service.ewallet.infrastructure.annotation.specification.SpecificationField;
import br.com.pix_service.ewallet.infrastructure.persistence.SpecificationOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@SpecificationEntity(value = TransactionEntity.class)
public class TransactionFilterTO {

    @Schema(description = "Wallet ID associated with the transaction", example = "123e4567-e89b-12d3-a456-426614174000")
    @SpecificationField(property = "walletId", operation = SpecificationOperation.LIKE)
    private String walletId;

    @Schema(description = "Timestamp indicating the earliest creation date of the transaction", example = "2024-06-15T14:30:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @SpecificationField(property = "createdAt", operation = SpecificationOperation.GREATER_THAN_OR_EQUAL)
    private LocalDateTime createdAt;

}
