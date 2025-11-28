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
public class PixTransferRequest implements Serializable {

    @Schema(description = "Identificador da carteira de origem", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
    private String fromWalletId;

    @Schema(description = "Chave Pix do destinatário", example = "(11) 91234-5678")
    private String toPixKey;

    @Schema(description = "Valor a ser transferido via Pix", example = "250.80")
    private BigDecimal amount;
}
