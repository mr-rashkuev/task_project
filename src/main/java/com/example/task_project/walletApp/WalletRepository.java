package com.example.task_project.walletApp;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    @Query("SELECT balance from WalletEntity where id=?1")
    Optional<BigDecimal> getWalletBalance(String walletId);

}
