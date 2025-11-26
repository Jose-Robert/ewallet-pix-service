package br.com.pix_service.ewallet.application.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletPixKeyRequest implements Serializable {

    private String pixKey;
}
