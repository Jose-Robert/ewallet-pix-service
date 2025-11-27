package br.com.pix_service.ewallet.domain.service;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.application.api.response.WalletTransactionBalanceResponse;
import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

public interface IWalletService {

    WalletResponse openWallet(WalletRequest walletRequest);
    WalletPixKeyResponse registerPixKey(PixKeyRequest pixKeyRequest);
    void deposit(DepositRequest depositRequest);
    void withdraw(WithdrawRequest withdrawRequest);
    WalletBalanceResponse getCurrentBalance(String walletId);
    WalletTransactionBalanceResponse getHistoricalBalance(Specification<TransactionEntity> specification, int page, int size);
}
