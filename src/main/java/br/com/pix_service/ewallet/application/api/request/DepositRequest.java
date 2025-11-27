package br.com.pix_service.ewallet.application.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DepositRequest implements Serializable {

    @Schema(description = "ID da carteira onde o depósito será realizado", example = "e7b8f8c2-3d4a-4f5b-9c6d-7e8f9a0b1c2d")
    private String walletId;

    @Schema(description = "Valor do depósito", example = "150.75")
    private BigDecimal amount;
}
