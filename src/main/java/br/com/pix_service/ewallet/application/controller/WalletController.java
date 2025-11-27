package br.com.pix_service.ewallet.application.controller;

import br.com.pix_service.ewallet.application.api.IWalletApi;
import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.domain.service.IWalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class WalletController implements IWalletApi {

    private final IWalletService walletService;

    @Override
    public ResponseEntity<WalletResponse> openWallet(WalletRequest walletRequest) {
        return new ResponseEntity<>(walletService.openWallet(walletRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<WalletPixKeyResponse> registerPixKey(PixKeyRequest pixKeyRequest) {
        return new ResponseEntity<>(walletService.registerPixKey(pixKeyRequest), HttpStatus.CREATED);
    }

    @Override
    public void deposit(DepositRequest depositRequest) {
        walletService.deposit(depositRequest);
    }

    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {
        walletService.withdraw(withdrawRequest);
    }

    @Override
    public ResponseEntity<WalletBalanceResponse> getCurrentBalance(String walletId) {
        return new ResponseEntity<>(walletService.getCurrentBalance(walletId), HttpStatus.OK);
    }
}
