package br.com.pix_service.ewallet.application.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletPixKeyResponse implements Serializable {

    private String walletId;
    private String pixKey;

}
