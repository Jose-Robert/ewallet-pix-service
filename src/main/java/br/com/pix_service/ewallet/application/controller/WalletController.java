package br.com.pix_service.ewallet.application.controller;

import br.com.pix_service.ewallet.application.api.IWalletApi;
import br.com.pix_service.ewallet.application.api.request.WalletPixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WalletController implements IWalletApi {

    @Override
    public ResponseEntity<WalletResponse> createWallet(WalletRequest walletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<WalletPixKeyResponse> registerPixKey(String walletId, WalletPixKeyRequest walletPixKeyRequest) {
        return null;
    }
}
