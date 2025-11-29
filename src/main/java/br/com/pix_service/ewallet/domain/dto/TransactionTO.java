package br.com.pix_service.ewallet.domain.dto;

import br.com.pix_service.ewallet.domain.enums.StatusType;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionTO implements Serializable {

    private UUID id;
    private String walletId;
    private TransactionType type;
    private BigDecimal amount;
    private String endToEndId;
    private StatusType status;
    private String toWalletId;
    private String idempotencyKey;
    private LocalDateTime createdAt;
}