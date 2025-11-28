package br.com.pix_service.ewallet.infrastructure.repository;

import br.com.pix_service.ewallet.domain.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findAll(Specification<TransactionEntity> specification, Pageable pageable);
    Optional<TransactionEntity> findByIdempotencyKey(String idempotencyKey);
}
