package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
import br.com.pix_service.ewallet.domain.dto.TransactionTO;
import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import br.com.pix_service.ewallet.domain.mapper.GenericMapper;
import br.com.pix_service.ewallet.domain.service.ITransactionService;
import br.com.pix_service.ewallet.infrastructure.repository.ITransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository repository;
    private final GenericMapper genericMapper;

    @Override
    public void saveTransaction(TransactionTO transactionTO) {
        var entity = genericMapper.map(transactionTO, TransactionEntity.class);
        repository.save(entity);
    }

    @Override
    public List<TransactionItemTO> getHistoricalBalance(Specification<TransactionEntity> specification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<TransactionEntity> transactions = repository.findAll(specification, pageable);
        return transactions.stream()
                .map(transaction -> genericMapper.map(transaction, TransactionItemTO.class))
                .toList();
    }

    @Override
    public TransactionTO getTransactionByIdempotencyKey(String idempotencyKey) {
        var entity = repository.findByIdempotencyKey(idempotencyKey);
        return entity.isPresent() ? genericMapper.map(entity, TransactionTO.class) : null;
    }
}
