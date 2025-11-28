package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.application.api.request.PixTransferRequest;
import br.com.pix_service.ewallet.application.api.request.PixWebhookEventRequest;
import br.com.pix_service.ewallet.application.api.response.PixTransferResponse;
import br.com.pix_service.ewallet.domain.dto.TransactionTO;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.enums.StatusType;
import br.com.pix_service.ewallet.domain.service.IPixTransferService;
import br.com.pix_service.ewallet.domain.service.ITransactionService;
import br.com.pix_service.ewallet.infrastructure.handler.exceptions.ObjectNotFoundException;
import br.com.pix_service.ewallet.infrastructure.handler.exceptions.PixTransferSameWalletException;
import br.com.pix_service.ewallet.infrastructure.repository.IWalletRepository;
import br.com.pix_service.ewallet.infrastructure.utils.EndToEndIdGeneratorUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.pix_service.ewallet.domain.enums.TransactionType.PIX_TRANSFER;
import static br.com.pix_service.ewallet.infrastructure.utils.Utils.validCurrentBalance;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@AllArgsConstructor
public class PixTransferServiceImpl implements IPixTransferService {

    private static final Logger log = LoggerFactory.getLogger(PixTransferServiceImpl.class);
    private final IWalletRepository walletRepository;
    private final ITransactionService transactionService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public PixTransferResponse transferPix(PixTransferRequest pixTransferRequest, String idempotencyKey) {
        var transaction = transactionService.getTransactionByIdempotencyKey(idempotencyKey);
        if (transaction != null && (transaction.getStatus() == StatusType.CONFIRMED || transaction.getStatus() == StatusType.PENDING)) {
            return PixTransferResponse.builder()
                    .endToEndId(transaction.getEndToEndId())
                    .status(transaction.getStatus().name())
                    .build();
        }
        var endToEndId = EMPTY;

        try {
            endToEndId = EndToEndIdGeneratorUtils.generateEndToEndId();
            var fromWallet = this.findWalletById(UUID.fromString(pixTransferRequest.getFromWalletId()));
            var balance = fromWallet.getBalance().subtract(pixTransferRequest.getAmount());
            validCurrentBalance(balance);
            fromWallet.setBalance(balance);

            var toWallet = this.findWalletByPixKey(pixTransferRequest.getToPixKey());
            toWallet.setBalance(toWallet.getBalance().add(pixTransferRequest.getAmount()));

            if (fromWallet.getId().equals(toWallet.getId())) {
                throw new PixTransferSameWalletException("Cannot transfer to the same wallet");
            }

            walletRepository.save(fromWallet);
            walletRepository.save(toWallet);

            saveTransactions(pixTransferRequest, idempotencyKey, fromWallet, toWallet, endToEndId);
            return PixTransferResponse.builder()
                    .endToEndId(endToEndId)
                    .status(StatusType.CONFIRMED.name())
                    .build();

        } catch (Exception e) {
            log.info("Pix transfer rejected: {}", e.getMessage());
            return PixTransferResponse.builder()
                    .endToEndId(endToEndId)
                    .status(StatusType.REJECTED.name())
                    .build();
        }

    }

    @Override
    public void pixWebhook(PixWebhookEventRequest webhookEventRequest) {
        // Implement the logic to handle the Pix webhook event
    }

    private void saveTransactions(PixTransferRequest pixTransferRequest, String idempotencyKey, WalletEntity fromWallet, WalletEntity toWallet, String endToEndId) {
        transactionService.saveTransaction(TransactionTO.builder()
                .id(UUID.randomUUID())
                .walletId(fromWallet.getId().toString())
                .toWalletId(toWallet.getId().toString())
                .type(PIX_TRANSFER)
                .amount(pixTransferRequest.getAmount())
                .endToEndId(endToEndId)
                .status(StatusType.CONFIRMED)
                .idempotencyKey(idempotencyKey)
                .build());
    }

    private WalletEntity findWalletById(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new ObjectNotFoundException("Wallet not found"));
    }

    private WalletEntity findWalletByPixKey(String pixKey) {
        return walletRepository.findByPixKey(pixKey).orElseThrow(() -> new ObjectNotFoundException("Wallet not found for the given Pix key"));
    }
}
