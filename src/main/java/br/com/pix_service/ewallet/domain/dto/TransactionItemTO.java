package br.com.pix_service.ewallet.domain.dto;

import br.com.pix_service.ewallet.domain.enums.TransactionType;
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

    private BigDecimal amount;
    private LocalDateTime createdAt;
    private TransactionType type;
}
