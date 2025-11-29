package br.com.pix_service.ewallet.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "idempotency", schema = "public")
public class IdempotencyEntity implements Serializable {

    @Id
    private String eventId;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @PrePersist
    public void prePersist() {
        this.processedAt = LocalDateTime.now();
    }

    public IdempotencyEntity(String eventId) {
        this.eventId = eventId;
    }
}
