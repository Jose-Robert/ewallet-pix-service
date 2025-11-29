package br.com.pix_service.ewallet.domain.service;

import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
import br.com.pix_service.ewallet.domain.dto.TransactionTO;
import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {

    void saveTransaction(TransactionTO transactionTO);
    List<TransactionItemTO> getHistoricalBalance(Specification<TransactionEntity> specification, int page, int size);
    TransactionTO getTransactionByIdempotencyKey(String idempotencyKey);
    TransactionTO getTransactionByEndToEndId(String endToEndId);
    void updateTransactionStatus(String endToEndId, String eventType, LocalDateTime occurredAt);
}
