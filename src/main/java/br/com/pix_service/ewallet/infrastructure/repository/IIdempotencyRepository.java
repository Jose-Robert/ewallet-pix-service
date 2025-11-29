package br.com.pix_service.ewallet.infrastructure.repository;

import br.com.pix_service.ewallet.domain.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIdempotencyRepository extends JpaRepository<IdempotencyEntity, String> {

    boolean existsByEventId(String eventId);
}
