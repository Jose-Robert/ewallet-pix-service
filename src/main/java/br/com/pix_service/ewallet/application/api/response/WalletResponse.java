package br.com.pix_service.ewallet.application.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletResponse implements Serializable {

    @Schema(description = "ID da carteira", example = "e7b8f8c2-3d4a-4f5b-9c6d-7e8f9a0b1c2d")
    private UUID id;

    @Schema(description = "Nome do titular da carteira", example = "João da Silva")
    private String name;

    @Schema(description = "Email do titular da carteira", example = "wallet@service.com.br")
    private String email;

    @Schema(description = "CPF do titular da carteira", example = "123.456.789-00")
    private String cpf;
}
