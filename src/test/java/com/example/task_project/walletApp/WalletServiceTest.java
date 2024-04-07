package com.example.task_project.walletApp;

import com.example.task_project.PersonApp.PersonEntity;
import com.example.task_project.exception.BusinessException;
import com.example.task_project.walletApp.dto.WalletTransactionRq;
import com.example.task_project.walletApp.operation.DepositOperation;
import com.example.task_project.walletApp.operation.Operation;
import com.example.task_project.walletApp.operation.WithDrawOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    private WalletService underTest;
    @Captor
    private ArgumentCaptor<WalletEntity> walletCaptor;


    @BeforeEach
    void setUp() {
        List<Operation> operationList = List.of(new DepositOperation(), new WithDrawOperation());
        underTest = new WalletService(walletRepository, operationList);
    }

    @Test
    void canGetWalletBalance() {
        when(walletRepository.getWalletBalance("walletId")).thenReturn(Optional.of(BigDecimal.ONE));

        BigDecimal expected = underTest.getWalletBalance("walletId");
        assertThat(expected).isEqualTo(BigDecimal.ONE);
    }
    @Test
    void cantGetWalletBalance() {
        when(walletRepository.getWalletBalance("walletId")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> underTest.getWalletBalance("walletId"));
        assertThat(exception.getMessage()).isEqualTo("Не найден кошелёк с id " + "walletId");
    }

    @Test
    void cantAcceptStringOperationTypeValue(){
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq("ID", "Deposit", new BigDecimal(10));

        BusinessException exception = assertThrows(BusinessException.class, () -> underTest.makeWalletTransaction(walletTransactionRq));
        assertThat(exception.getMessage()).isEqualTo("Operation может принимать значения DEPOSIT или WITHDRAW");
    }

    @Test
    void canDoDepositOperation() {
        WalletEntity wallet = new WalletEntity(new PersonEntity("Andrew"), new BigDecimal(500));
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq("ID", "DEPOSIT", new BigDecimal(10));

        when(walletRepository.findById(walletTransactionRq.getWalletId())).thenReturn(Optional.of(wallet));

        underTest.makeWalletTransaction(walletTransactionRq);

        verify(walletRepository).save(walletCaptor.capture());
        WalletEntity capturedWallet = walletCaptor.getValue();
        assertThat(capturedWallet.getBalance()).isEqualTo(BigDecimal.valueOf(510));
    }

    @Test
    void canDoWithdrawOperation() {
        WalletEntity wallet = new WalletEntity(new PersonEntity("Andrew"), new BigDecimal(500));
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq("ID", "WITHDRAW", new BigDecimal(10));

        when(walletRepository.findById(walletTransactionRq.getWalletId())).thenReturn(Optional.of(wallet));

        underTest.makeWalletTransaction(walletTransactionRq);

        verify(walletRepository).save(walletCaptor.capture());
        WalletEntity capturedWallet = walletCaptor.getValue();
        assertThat(capturedWallet.getBalance()).isEqualTo(BigDecimal.valueOf(490));
    }

    @Test
    void cantDoWithdrawOperation() {
        WalletEntity wallet = new WalletEntity(new PersonEntity("Andrew"), new BigDecimal(500));
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq("ID", "WITHDRAW", new BigDecimal(510));

        when(walletRepository.findById(walletTransactionRq.getWalletId())).thenReturn(Optional.of(wallet));

        BusinessException exception = assertThrows(BusinessException.class, ()->underTest.makeWalletTransaction(walletTransactionRq));
        assertThat(exception.getMessage()).isEqualTo("Недостаточно средств для проведения операции");
    }
}