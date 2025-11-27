package br.com.pix_service.ewallet.application.api;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
public interface IWalletApi {

    @Operation(summary = "Create Wallet", description = "Creating a new wallet for a user.")
    @PostMapping
    ResponseEntity<WalletResponse> openWallet(@RequestBody WalletRequest walletRequest);

    @Operation(summary = "Register Pix Key", description = "Registering a Pix key to an existing wallet.")
    @PostMapping(value = "/pix-keys")
    ResponseEntity<WalletPixKeyResponse> registerPixKey(@RequestBody PixKeyRequest pixKeyRequest);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deposit Value", description = "Deposit funds into a wallet.")
    @PostMapping(value = "/deposits")
    void deposit(@RequestBody DepositRequest depositRequest);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Withdraw Value", description = "Withdraw funds from a wallet.")
    @PostMapping(value = "/withdrawals")
    void withdraw(@RequestBody WithdrawRequest withdrawRequest);

    @Operation(summary = "Get Current Balance", description = "Retrieve the current balance of a wallet.")
    @GetMapping(value = "/{walletId}/balance")
    ResponseEntity<WalletBalanceResponse> getCurrentBalance(@PathVariable("walletId") String walletId);
}
