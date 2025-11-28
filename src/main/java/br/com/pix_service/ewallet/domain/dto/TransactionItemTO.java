package br.com.pix_service.ewallet.domain.dto;

import br.com.pix_service.ewallet.domain.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionItemTO implements Serializable {

    @Schema(description = "Transaction amount", example = "150.75")
    private BigDecimal amount;

    @Schema(description = "Transaction creation timestamp", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Transaction type", example = "DEPOSIT")
    private TransactionType type;
}
