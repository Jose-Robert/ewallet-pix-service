package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.application.api.request.WithdrawRequest;
import br.com.pix_service.ewallet.application.api.response.WalletPixKeyResponse;
import br.com.pix_service.ewallet.application.api.response.WalletResponse;
import br.com.pix_service.ewallet.domain.dto.TransactionItemTO;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.enums.KeyType;
import br.com.pix_service.ewallet.domain.enums.TransactionType;
import br.com.pix_service.ewallet.domain.mapper.GenericMapper;
import br.com.pix_service.ewallet.domain.service.ITransactionService;
import br.com.pix_service.ewallet.infrastructure.repository.IWalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static br.com.pix_service.ewallet.builder.WalletBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @InjectMocks private WalletServiceImpl walletService;
    @Mock private IWalletRepository repository;
    @Mock private GenericMapper genericMapper;
    @Mock private ITransactionService transactionService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(walletService, "genericMapper", genericMapper);
        ReflectionTestUtils.setField(walletService, "transactionService", transactionService);
    }


    @Test
    @DisplayName("Deve abrir uma nova carteira com sucesso")
    void testOpenWallet() {
        var request = buildWalletRequest();
        var entity = buildWalletEntity();
        var response = buildWalletResponse();

        when(genericMapper.map(request, WalletEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(genericMapper.map(entity, WalletResponse.class)).thenReturn(response);

        var walletResponse = walletService.openWallet(request);
        assertNotNull(walletResponse);
        assertAll(
                () -> assertEquals("***.456.789-**", walletResponse.getCpf()),
                () -> assertEquals("João Silva", walletResponse.getName()),
                () -> assertEquals("joao_silva@teste.com", walletResponse.getEmail())
        );
    }


    @Test
    @DisplayName("Deve registrar chave EMAIL/PHONE/EVP")
    void testRegisterPixKey() {
        var request = buildPixKeyResquet();
        var entity = buildWalletEntity();

        when(repository.findById(UUID.fromString(request.getWalletId()))).thenReturn(Optional.ofNullable(entity));
        when(genericMapper.map(repository.save(Objects.requireNonNull(entity)), WalletPixKeyResponse.class)).thenReturn(buildPixKeyResponse());

        var response = walletService.registerPixKey(request);
        assertAll(
                () -> assertEquals("98cf794d-72c9-4d4b-b500-1a188263bcb0", response.getWalletId()),
                () -> assertEquals("83996154232", response.getPixKey())
        );
    }


    @Test
    @DisplayName("Deve realizar um deposito em carteira - Credito")
    void testDeposit() {
        var request = buildDepositRequest();
        var entity = buildWalletEntity();
        entity.setKeyType(KeyType.PHONE);
        entity.setPixKey("83996154232");

        when(repository.findById(UUID.fromString(request.getWalletId()))).thenReturn(Optional.of(entity));

        ArgumentCaptor<WalletEntity> captor = ArgumentCaptor.forClass(WalletEntity.class);
        walletService.deposit(request);

        verify(repository).save(captor.capture());
        var saved = captor.getValue();
        assertEquals(new BigDecimal("100.00"), saved.getBalance());
    }

    @Test
    @DisplayName("Deve realizar um saque em carteira - Debíto")
    void testWithdraw() {
        var request = WithdrawRequest.builder()
                .walletId("98cf794d-72c9-4d4b-b500-1a188263bcb0")
                .amount(new BigDecimal("50"))
                .build();
        var entity = buildWalletEntity();
        entity.setKeyType(KeyType.PHONE);
        entity.setPixKey("83996154232");
        entity.setBalance(new BigDecimal("100"));

        when(repository.findById(UUID.fromString(request.getWalletId()))).thenReturn(Optional.of(entity));

        ArgumentCaptor<WalletEntity> captor = ArgumentCaptor.forClass(WalletEntity.class);
        walletService.withdraw(request);

        verify(repository).save(captor.capture());
        var saved = captor.getValue();
        assertEquals(new BigDecimal("50"), saved.getBalance());

    }

    @Test
    @DisplayName("Deve consultar saldo da carteira")
    void testGetCurrentBalance() {
        var walletId = "98cf794d-72c9-4d4b-b500-1a188263bcb0";
        var entity = buildWalletEntity();
        entity.setBalance(new BigDecimal("250.75"));

        when(repository.findById(UUID.fromString(walletId))).thenReturn(Optional.of(entity));

        var response = walletService.getCurrentBalance(walletId);
        Assertions.assertAll(
                () -> assertEquals(new BigDecimal("250.75"), response.getAmount())
        );

    }

    @Test
    @DisplayName("Deve consultar saldo da carteira por histórico")
    void testGetHistoricalBalance() {
        var transactionItem1 = TransactionItemTO.builder()
                .amount(new BigDecimal("100.00"))
                .type(TransactionType.DEPOSIT)
                .createdAt(OffsetDateTime.parse("2025-11-29T03:22:55.82Z").toLocalDateTime())
                .build();
        var transactionItem2 = TransactionItemTO.builder()
                .amount(new BigDecimal("50.00"))
                .type(TransactionType.WITHDRAW)
                .createdAt(OffsetDateTime.parse("2025-11-29T03:50:40.07Z").toLocalDateTime())
                .build();
        List<TransactionItemTO> transactions = new ArrayList<>(List.of(transactionItem1, transactionItem2));

        when(transactionService.getHistoricalBalance(any(), anyInt(), anyInt())).thenReturn(transactions);

        var response = walletService.getHistoricalBalance(any(), eq(0), eq(5));
        Assertions.assertAll(
                () -> assertFalse(response.getTransactions().isEmpty()),
                () -> assertEquals(2, response.getTransactions().size())
        );
    }
}