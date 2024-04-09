package com.example.task_project.walletApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    @Query("SELECT balance from WalletEntity where id=?1")
    Optional<BigDecimal> getWalletBalance(String walletId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletEntity> findWalletById(String walletId);
}
