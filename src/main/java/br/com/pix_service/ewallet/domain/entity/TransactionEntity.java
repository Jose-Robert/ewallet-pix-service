package br.com.pix_service.ewallet.domain.entity;

import br.com.pix_service.ewallet.domain.enums.StatusType;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "transactions", schema = "public")
public class TransactionEntity implements Serializable {

    @Id
    private UUID id;

    private String walletId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(unique = true)
    private String endToEndId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private StatusType status;

    private String toWalletId;

    @Column(unique = true)
    private String idempotencyKey;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    protected Integer version;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
