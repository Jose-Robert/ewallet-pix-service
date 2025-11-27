package br.com.pix_service.ewallet.application.api.filter;

import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import br.com.pix_service.ewallet.infrastructure.persistence.SpecificationOperation;
import br.com.pix_service.ewallet.infrastructure.specification.SpecificationEntity;
import br.com.pix_service.ewallet.infrastructure.specification.SpecificationField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@SpecificationEntity(value = TransactionEntity.class)
public class TransactionFilterTO {

    @SpecificationField(property = "walletId", operation = SpecificationOperation.LIKE)
    private String walletId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @SpecificationField(property = "createdAt", operation = SpecificationOperation.GREATER_THAN_OR_EQUAL)
    private LocalDateTime createdAt;

}
