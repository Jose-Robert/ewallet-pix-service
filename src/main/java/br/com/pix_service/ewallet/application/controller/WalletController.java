package br.com.pix_service.ewallet.application.controller;

import br.com.pix_service.ewallet.application.api.IWalletApi;
import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WalletController implements IWalletApi {

    @Override
    public ResponseEntity<WalletResponse> openWallet(WalletRequest walletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<WalletPixKeyResponse> registerPixKey(PixKeyRequest pixKeyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deposit(DepositRequest depositRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> withdraw(WithdrawRequest withdrawRequest) {
        return null;
    }

    @Override
    public ResponseEntity<WalletBalanceResponse> getCurrentBalance(String walletId) {
        return null;
    }
}
