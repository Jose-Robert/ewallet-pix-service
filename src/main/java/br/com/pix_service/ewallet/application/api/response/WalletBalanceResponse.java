package br.com.pix_service.ewallet.application.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletBalanceResponse {

    @Schema(description = "Saldo atual da carteira", example = "2500.00")
    private BigDecimal amount;
}
