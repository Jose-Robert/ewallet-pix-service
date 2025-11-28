package br.com.pix_service.ewallet.domain.entity;

import br.com.pix_service.ewallet.domain.enums.KeyType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "wallets", schema = "public")
public class WalletEntity implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "pix_key")
    private String pixKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "key_type")
    private KeyType keyType;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    protected Integer version;

}
