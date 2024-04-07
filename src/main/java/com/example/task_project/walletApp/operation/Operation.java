package com.example.task_project.walletApp.operation;

import com.example.task_project.walletApp.OperationType;
import com.example.task_project.walletApp.WalletEntity;
import com.example.task_project.walletApp.dto.WalletTransactionRq;

import java.math.BigDecimal;

public interface Operation {

    BigDecimal apply(BigDecimal walletBalance, BigDecimal amount);

    OperationType getOperationType();
}
