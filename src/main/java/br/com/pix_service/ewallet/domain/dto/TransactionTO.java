package br.com.pix_service.ewallet.domain.dto;

import br.com.pix_service.ewallet.domain.enums.Status;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionTO implements Serializable {

    private UUID id;
    private String walletId;
    private TransactionType type;
    private BigDecimal amount;
    private String endToEndId;
    private Status status;
    private String toWalletId;
    private String idempotencyKey;
}