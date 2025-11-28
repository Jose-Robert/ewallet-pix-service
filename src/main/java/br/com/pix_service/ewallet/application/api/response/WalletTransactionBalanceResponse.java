package br.com.pix_service.ewallet.application.api.response;

import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletTransactionBalanceResponse implements Serializable {

    @Schema(description = "List of transactions associated with the wallet")
    private List<TransactionItemTO> transactions;
}
