package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.mapper.GenericMapper;
import br.com.pix_service.ewallet.domain.service.IWalletService;
import br.com.pix_service.ewallet.infrastructure.exceptions.InvalidArgumentException;
import br.com.pix_service.ewallet.infrastructure.repository.IWalletRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepository walletRepository;
    private final GenericMapper genericMapper;


    @Override
    public WalletResponse openWallet(WalletRequest walletRequest) {
        var walletEntity = genericMapper.map(walletRequest, WalletEntity.class);
        walletEntity.setBalance(new BigDecimal("0.00"));
        return genericMapper.map(walletRepository.save(walletEntity), WalletResponse.class);
    }

    @Override
    public WalletPixKeyResponse registerPixKey(PixKeyRequest pixKeyRequest) {
        var entity = this.findWalletById(UUID.fromString(pixKeyRequest.getWalletId()));
        entity.setPixKey(pixKeyRequest.getPixKey());
        return genericMapper.map(walletRepository.save(entity), WalletPixKeyResponse.class);
    }

    @Transactional
    @Override
    public void deposit(DepositRequest depositRequest) {
        var entity = this.findWalletById(UUID.fromString(depositRequest.getWalletId()));
        var amount = depositRequest.getAmount();
        this.validateInputAmount(amount);
        var current = entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance();
        entity.setBalance(current.add(amount));
        walletRepository.save(entity);
    }

    @Transactional
    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {
        var entity = this.findWalletById(UUID.fromString(withdrawRequest.getWalletId()));
        var amount = withdrawRequest.getAmount();
        this.validateInputAmount(amount);
        var currentAmount = entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance();
        var balance = currentAmount.subtract(amount);
        this.validCurrentBalance(balance);
        entity.setBalance(balance);
        walletRepository.save(entity);
    }

    @Override
    public WalletBalanceResponse getCurrentBalance(String walletId) {
        var entity = this.findWalletById(UUID.fromString(walletId));
        return WalletBalanceResponse.builder()
                .amount(entity.getBalance())
                .build();
    }

    private WalletEntity findWalletById(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new InvalidArgumentException("Wallet not found"));
    }

    private void validateInputAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Invalid deposit amount");
        }
    }

    private void validCurrentBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidArgumentException("Insufficient funds for withdrawal");
        }
    }
}
