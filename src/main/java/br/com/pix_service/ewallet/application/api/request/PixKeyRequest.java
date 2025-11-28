package br.com.pix_service.ewallet.application.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PixKeyRequest implements Serializable {

    @Schema(description = "ID da carteira associada à chave Pix", example = "e7b8f8c2-3d4a-4f5b-9c6d-7e8f9a0b1c2d")
    private String walletId;

    @Schema(description = "Chave Pix do titular da carteira", example = "email/telefone/EVP")
    private String pixKey;
}
