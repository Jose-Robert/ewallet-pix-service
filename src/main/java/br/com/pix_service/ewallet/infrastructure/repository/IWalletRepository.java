package br.com.pix_service.ewallet.infrastructure.repository;

import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWalletRepository extends JpaRepository<WalletEntity, UUID> {

    Optional<WalletEntity> findByPixKey(String pixKey);
}
