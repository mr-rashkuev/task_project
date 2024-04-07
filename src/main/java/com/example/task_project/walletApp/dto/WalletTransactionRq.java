package com.example.task_project.walletApp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class WalletTransactionRq {

    @NotNull(message = "Поле walletId не может быть пустым")
    private String walletId;

    @NotNull(message = "Поле operation не может быть пустым")
    private String operation;

    @NotNull(message = "Поле amount не может быть пустым")
    private BigDecimal amount;

    public WalletTransactionRq(String walletId, String operation, BigDecimal amount) {
        this.walletId = walletId;
        this.operation = operation;
        this.amount = amount;
    }
}
