package br.com.pix_service.ewallet.application.controller;

import br.com.pix_service.ewallet.application.api.IWalletApi;
import br.com.pix_service.ewallet.application.api.filter.TransactionFilterTO;
import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.application.api.response.WalletTransactionBalanceResponse;
import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import br.com.pix_service.ewallet.domain.service.IWalletService;
import br.com.pix_service.ewallet.infrastructure.persistence.SpecificationFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@AllArgsConstructor
public class WalletController implements IWalletApi {

    private final IWalletService walletService;
    private final SpecificationFactory<TransactionEntity> specificationFactory;

    @Override
    public ResponseEntity<WalletResponse> openWallet(WalletRequest walletRequest) {
        return new ResponseEntity<>(walletService.openWallet(walletRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<WalletPixKeyResponse> registerPixKey(PixKeyRequest pixKeyRequest) {
        return new ResponseEntity<>(walletService.registerPixKey(pixKeyRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> deposit(DepositRequest depositRequest) {
        walletService.deposit(depositRequest);
        return ResponseEntity.ok(Map.of(
                "message", "Deposit successful",
                "walletId", depositRequest.getWalletId(),
                "amount", depositRequest.getAmount()));
    }

    @Override
    public ResponseEntity<Object> withdraw(WithdrawRequest withdrawRequest) {
        walletService.withdraw(withdrawRequest);
        return ResponseEntity.ok(Map.of(
                "message", "Withdrawal successful",
                "walletId", withdrawRequest.getWalletId(),
                "amount", withdrawRequest.getAmount()));
    }

    @Override
    public ResponseEntity<WalletBalanceResponse> getCurrentBalance(String walletId) {
        return new ResponseEntity<>(walletService.getCurrentBalance(walletId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletTransactionBalanceResponse> getHistoricalBalance(TransactionFilterTO filterTO, int page, int size) {
        Specification<TransactionEntity> specification = specificationFactory.create(filterTO);
        return new ResponseEntity<>(walletService.getHistoricalBalance(specification, page, size), HttpStatus.OK);
    }
}
