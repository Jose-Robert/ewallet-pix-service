package br.com.pix_service.ewallet.application.api;

import br.com.pix_service.ewallet.application.api.request.WalletPixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
public interface IWalletApi {

    @Operation(summary = "Create Wallet", description = "Creating a new wallet for a user.")
    @PostMapping
    ResponseEntity<WalletResponse> createWallet(@RequestBody WalletRequest walletRequest);

    @Operation(summary = "Register Pix Key", description = "Registering a Pix key to an existing wallet.")
    @PostMapping(value = "/{walletId}/pix-keys")
    ResponseEntity<WalletPixKeyResponse> registerPixKey(@PathVariable("walletId") String walletId, @RequestBody WalletPixKeyRequest walletPixKeyRequest);


}
