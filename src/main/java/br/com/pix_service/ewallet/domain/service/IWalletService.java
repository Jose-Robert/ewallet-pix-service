package br.com.pix_service.ewallet.domain.service;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;

public interface IWalletService {

    WalletResponse openWallet(WalletRequest walletRequest);
    WalletPixKeyResponse registerPixKey(PixKeyRequest pixKeyRequest);
    void deposit(DepositRequest depositRequest);
    void withdraw(WithdrawRequest withdrawRequest);
    WalletBalanceResponse getCurrentBalance(String walletId);
}
