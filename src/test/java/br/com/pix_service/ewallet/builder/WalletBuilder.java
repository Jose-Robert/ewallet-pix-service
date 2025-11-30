package br.com.pix_service.ewallet.builder;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class WalletBuilder {

    public static WalletRequest buildWalletRequest() {
        return WalletRequest.builder()
                .cpf("123.456.789-00")
                .name("João Silva")
                .email("joao_silva@teste.com")
                .build();
    }

    public static WalletEntity buildWalletEntity() {
        return WalletEntity.builder()
                .id(UUID.fromString("98cf794d-72c9-4d4b-b500-1a188263bcb0"))
                .cpf("12345678900")
                .name("João Silva")
                .email("joao_silva@teste.com")
                .balance(new BigDecimal("0.00"))
                .build();
    }

    public static WalletResponse buildWalletResponse() {
        var entity = buildWalletEntity();
        return WalletResponse.builder()
                .id(entity.getId())
                .cpf(entity.getCpf())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    public static PixKeyRequest buildPixKeyResquet() {
        return PixKeyRequest.builder()
                .walletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .pixKey("(83) 99615-4232")
                .keyType("PHONE")
                .build();
    }

    public static WalletPixKeyResponse buildPixKeyResponse() {
        return WalletPixKeyResponse.builder()
                .walletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .pixKey("83996154232")
                .build();
    }

    public static DepositRequest buildDepositRequest() {
        return DepositRequest.builder()
                .walletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .amount(new BigDecimal("100.0"))
                .build();
    }
}
