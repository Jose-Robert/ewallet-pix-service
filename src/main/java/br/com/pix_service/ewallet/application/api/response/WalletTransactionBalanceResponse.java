package br.com.pix_service.ewallet.application.api.response;

import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
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

    private List<TransactionItemTO> transactions;
}
