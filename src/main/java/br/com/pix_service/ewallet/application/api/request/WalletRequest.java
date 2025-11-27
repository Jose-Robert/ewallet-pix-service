package br.com.pix_service.ewallet.application.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletRequest implements Serializable {

    @Schema(description = "Nome do titular da carteira", example = "João Silva")
    private String name;

    @Schema(description = "Email do titular da carteira", example = "wallet@service.com.br")
    private String email;

    @Schema(description = "CPF do titular da carteira", example = "123.456.789-00")
    private String cpf;
}
