package br.com.pix_service.ewallet.application.api.request;

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
public class WithdrawRequest implements Serializable {

    private String walletId;
    private BigDecimal amount;
}
