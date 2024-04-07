package com.example.task_project.walletApp.operation;

import com.example.task_project.exception.BusinessException;
import com.example.task_project.walletApp.OperationType;
import com.example.task_project.walletApp.WalletEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithDrawOperation implements Operation{

    @Override
    public BigDecimal apply(BigDecimal walletBalance, BigDecimal amount) {
        if(walletBalance.compareTo(amount)<0){
            throw new BusinessException("Недостаточно средств для проведения операции");
        }
        return walletBalance.subtract(amount);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.WITHDRAW;
    }
}
