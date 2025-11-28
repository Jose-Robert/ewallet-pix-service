package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.application.api.request.DepositRequest;
import br.com.pix_service.ewallet.application.api.request.PixKeyRequest;
import br.com.pix_service.ewallet.application.api.request.WalletRequest;
import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletBalanceResponse;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.application.api.response.WalletTransactionBalanceResponse;
import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
import br.com.pix_service.ewallet.domain.dto.TransactionTO;
import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import br.com.pix_service.ewallet.domain.mapper.GenericMapper;
import br.com.pix_service.ewallet.domain.service.ITransactionService;
import br.com.pix_service.ewallet.domain.service.IWalletService;
import br.com.pix_service.ewallet.infrastructure.handler.exceptions.ObjectNotFoundException;
import br.com.pix_service.ewallet.infrastructure.repository.IWalletRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static br.com.pix_service.ewallet.infrastructure.utils.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepository walletRepository;
    private final GenericMapper genericMapper;
    private final ITransactionService transactionService;


    @Override
    public WalletResponse openWallet(WalletRequest walletRequest) {
        walletRequest.setCpf(cleanCPFAndPhone(walletRequest.getCpf()));
        var walletEntity = genericMapper.map(walletRequest, WalletEntity.class);
        walletEntity.setBalance(new BigDecimal("0.00"));
        var walletSaved = walletRepository.save(walletEntity);
        var response = genericMapper.map(walletSaved, WalletResponse.class);
        response.setCpf(formatAndMaskCpf(response.getCpf()));
        return response;
    }

    @Override
    public WalletPixKeyResponse registerPixKey(PixKeyRequest pixKeyRequest) {
        if (!isBlank(pixKeyRequest.getKeyType()) && pixKeyRequest.getKeyType().equalsIgnoreCase("PHONE")) {
            pixKeyRequest.setPixKey(cleanCPFAndPhone(pixKeyRequest.getPixKey()));
        }

        var entity = this.findWalletById(UUID.fromString(pixKeyRequest.getWalletId()));
        entity.setPixKey(pixKeyRequest.getPixKey());
        return genericMapper.map(walletRepository.save(entity), WalletPixKeyResponse.class);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deposit(DepositRequest depositRequest) {
        var entity = this.findWalletById(UUID.fromString(depositRequest.getWalletId()));
        var amount = depositRequest.getAmount();
        validateInputAmount(amount);
        var current = entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance();
        entity.setBalance(current.add(amount));
        walletRepository.save(entity);
        var transactionTO = TransactionTO.builder()
                .id(UUID.randomUUID())
                .walletId(depositRequest.getWalletId())
                .type(TransactionType.DEPOSIT)
                .amount(entity.getBalance())
                .build();
        transactionService.saveTransaction(transactionTO);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {
        var entity = this.findWalletById(UUID.fromString(withdrawRequest.getWalletId()));
        var amount = withdrawRequest.getAmount();
        validateInputAmount(amount);
        var currentAmount = entity.getBalance() == null ? BigDecimal.ZERO : entity.getBalance();
        var balance = currentAmount.subtract(amount);
        validCurrentBalance(balance);
        entity.setBalance(balance);
        walletRepository.save(entity);

        var transactionTO = TransactionTO.builder()
                .id(UUID.randomUUID())
                .walletId(withdrawRequest.getWalletId())
                .type(TransactionType.WITHDRAW)
                .amount(balance)
                .build();
        transactionService.saveTransaction(transactionTO);
    }

    @Override
    public WalletBalanceResponse getCurrentBalance(String walletId) {
        var entity = this.findWalletById(UUID.fromString(walletId));
        return WalletBalanceResponse.builder()
                .amount(entity.getBalance())
                .build();
    }

    @Override
    public WalletTransactionBalanceResponse getHistoricalBalance(Specification<TransactionEntity> specification, int page, int size) {
        List<TransactionItemTO> transactions = transactionService.getHistoricalBalance(specification, page, size);
        return WalletTransactionBalanceResponse.builder().transactions(transactions).build();
    }

    private WalletEntity findWalletById(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new ObjectNotFoundException("Wallet not found"));
    }
}
