package com.example.task_project.walletApp.operation;

import com.example.task_project.walletApp.OperationType;
import com.example.task_project.walletApp.WalletEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DepositOperation implements Operation{
    @Override
    public BigDecimal apply(BigDecimal walletBalance, BigDecimal amount) {
       return walletBalance.add(amount);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.DEPOSIT;
    }
}
