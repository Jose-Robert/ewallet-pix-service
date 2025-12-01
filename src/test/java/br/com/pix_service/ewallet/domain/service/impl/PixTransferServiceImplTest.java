package br.com.pix_service.ewallet.domain.service.impl;

import br.com.pix_service.ewallet.domain.entity.IdempotencyEntity;
import br.com.pix_service.ewallet.domain.entity.WalletEntity;
import br.com.pix_service.ewallet.domain.enums.KeyType;
import br.com.pix_service.ewallet.domain.enums.StatusType;
import br.com.pix_service.ewallet.domain.service.ITransactionService;
import br.com.pix_service.ewallet.infrastructure.repository.IIdempotencyRepository;
import br.com.pix_service.ewallet.infrastructure.repository.IWalletRepository;
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
import java.util.Optional;
import java.util.UUID;

import static br.com.pix_service.ewallet.builder.PixTransferBuilder.*;
import static br.com.pix_service.ewallet.builder.WalletBuilder.buildWalletEntity;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PixTransferServiceImplTest {

    @InjectMocks private PixTransferServiceImpl service;
    @Mock private IWalletRepository walletRepository;
    @Mock private ITransactionService transactionService;
    @Mock private IIdempotencyRepository idempotencyRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "transactionService", transactionService);
    }

    @Test
    @DisplayName("Deve realizar transferência PIX entre carteiras")
    void testTransferPix() {
        var request = buildPixTransferRequest();
        var idempotencyKey = "71f0efcc-4f89-4e53-9efc-1a786af3edc2";
        var entityFrom = buildWalletEntity();
        entityFrom.setBalance(new BigDecimal("1000.00"));
        entityFrom.setKeyType(KeyType.PHONE);
        entityFrom.setPixKey("83996154232");

        var toEntity = buildToWalletEntity();

        when(walletRepository.findById(UUID.fromString(request.getFromWalletId()))).thenReturn(Optional.of(entityFrom));
        when(walletRepository.findByPixKey(request.getToPixKey())).thenReturn(Optional.of(toEntity));

        var response = service.transferPix(request, idempotencyKey);
        ArgumentCaptor<WalletEntity> captor = ArgumentCaptor.forClass(WalletEntity.class);

        verify(walletRepository, times(2)).save(captor.capture());

        var saved = captor.getAllValues();
        var originSaved = saved.get(0);
        var targetSaved = saved.get(1);

        assertAll(
                () -> assertEquals(new BigDecimal("780.00"), originSaved.getBalance()),
                () -> assertEquals(new BigDecimal("220.00"), targetSaved.getBalance()),
                () -> assertEquals(StatusType.PENDING.name(), response.getStatus())
        );
    }


    @Test
    @DisplayName("Deve verificar se já existe uma transação CONFIRMED/PENDING pix com o mesmo idempotencyKey")
    void testTransferPixIdempotencyKeyConfirmedOrPending() {
        var request = buildPixTransferRequest();
        var idempotencyKey = "71f0efcc-4f89-4e53-9efc-1a786af3edc2";
        var transaction = buildTransactionTo();

        when(transactionService.getTransactionByIdempotencyKey(idempotencyKey)).thenReturn(transaction);

        var response = service.transferPix(request, idempotencyKey);
        assertAll(
                () -> assertEquals("E2E202511290022553270FB47", response.getEndToEndId()),
                () -> assertEquals("PENDING", response.getStatus())
        );
    }

    @Test
    @DisplayName("Deve verificar se a transferência está sendo realizada para a mesma carteira")
    void testTransferPixSameWallet() {
        var request = buildPixTransferRequest();
        var idempotencyKey = "71f0efcc-4f89-4e53-9efc-1a786af3edc2";
        var entityFrom = buildWalletEntity();
        entityFrom.setBalance(new BigDecimal("1000.00"));
        entityFrom.setKeyType(KeyType.PHONE);
        entityFrom.setPixKey("83996154232");

        var toEntity = buildToWalletEntity();
        toEntity.setId(UUID.fromString("98cf794d-72c9-4d4b-b500-1a188263bcb0"));

        when(walletRepository.findById(UUID.fromString(request.getFromWalletId()))).thenReturn(Optional.of(entityFrom));
        when(walletRepository.findByPixKey(request.getToPixKey())).thenReturn(Optional.of(toEntity));

        var response = service.transferPix(request, idempotencyKey);

        assertAll(
                () -> assertEquals(EMPTY, response.getEndToEndId()),
                () -> assertEquals("ERROR", response.getStatus())
        );
    }

    @Test
    @DisplayName("Deve executar confirmações/negações de transferência PIX")
    void testExecuteWebhook() {
        var request = buildWebhookRequest();
        var transaction = buildTransactionTo();

        when(idempotencyRepository.existsByEventId(request.getEventId())).thenReturn(false);
        when(transactionService.getTransactionByEndToEndId(request.getEndToEndId())).thenReturn(transaction);

        service.executeWebhook(request);
        ArgumentCaptor<IdempotencyEntity> captor = ArgumentCaptor.forClass(IdempotencyEntity.class);

        verify(idempotencyRepository).save(captor.capture());

        var saved = captor.getValue();
        assertAll(
                () -> assertEquals("EVT-20251129004616", saved.getEventId())
        );
    }

    @Test
    @DisplayName("Não deve processar webhook quando eventId já existe")
    void testverifyInputEventId() {
        var request = buildWebhookRequest();
        when(idempotencyRepository.existsByEventId(request.getEventId())).thenReturn(true);

        service.executeWebhook(request);
        verify(idempotencyRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve processar webhook quando a data da transação é depois do evento")
    void testDateTransactionAfterEvent() {
        var request = buildWebhookRequest();
        var transaction = buildTransactionTo();
        transaction.setCreatedAt(OffsetDateTime.parse("2025-11-29T04:22:55.82Z").toLocalDateTime());

        when(idempotencyRepository.existsByEventId(request.getEventId())).thenReturn(false);
        when(transactionService.getTransactionByEndToEndId(request.getEndToEndId())).thenReturn(transaction);

        service.executeWebhook(request);
        ArgumentCaptor<IdempotencyEntity> captor = ArgumentCaptor.forClass(IdempotencyEntity.class);

        verify(idempotencyRepository).save(captor.capture());

        var saved = captor.getValue();
        assertAll(
                () -> assertEquals("EVT-20251129004616", saved.getEventId())
        );
    }

    @Test
    @DisplayName("Deve executar negação da transferência PIX")
    void testExecuteWebhookRejectedPixTransfer() {
        var request = buildWebhookRequest();
        request.setEventType("REJECTED");
        var transaction = buildTransactionTo();
        var walletFrom = buildWalletEntity();
        walletFrom.setBalance(new BigDecimal("1000.00"));
        walletFrom.setKeyType(KeyType.PHONE);
        walletFrom.setPixKey("83996154232");
        var toEntity = buildToWalletEntity();

        when(idempotencyRepository.existsByEventId(request.getEventId())).thenReturn(false);
        when(transactionService.getTransactionByEndToEndId(request.getEndToEndId())).thenReturn(transaction);
        when(walletRepository.findById(UUID.fromString("98cf794d-72c9-4d4b-b500-1a188263bcb0"))).thenReturn(Optional.of(walletFrom));
        when(walletRepository.findById(UUID.fromString("d68746eb-de23-407c-ad43-194263d10196"))).thenReturn(Optional.of(toEntity));

        service.executeWebhook(request);
        ArgumentCaptor<IdempotencyEntity> captor = ArgumentCaptor.forClass(IdempotencyEntity.class);

        verify(idempotencyRepository).save(captor.capture());

        var saved = captor.getValue();
        assertAll(
                () -> assertEquals("EVT-20251129004616", saved.getEventId())
        );
    }
}